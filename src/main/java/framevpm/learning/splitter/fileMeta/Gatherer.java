package framevpm.learning.splitter.fileMeta;

import data7.model.Data7;
import data7.model.change.Commit;
import data7.model.vulnerability.Vulnerability;
import data7.project.ProjectFactory;
import framevpm.ExporterExtended;
import framevpm.ResourcesPathExtended;

import framevpm.project.ProjectInfoFactory;

import java.io.IOException;
import java.util.*;

public class Gatherer {

    private final ExporterExtended exporter;

    private final String project;
    private final Map<String, Map<String, VulnerabilityInfo>> projectData;

    private final TreeMap<Long, String> releases;
    private final Map<String, String> releasesCVE;

    public Gatherer(ResourcesPathExtended resourcesPathExtended, String project) {
        this.exporter = new ExporterExtended(resourcesPathExtended);
        this.project = project;
        if (ProjectFactory.retrieveProjectInfo(project) == null) {
            throw new RuntimeException("Incorrect Project");
        }
        this.projectData = new LinkedHashMap<>();
        releases = ProjectInfoFactory.retrieveProjectRelease(project);
        releasesCVE = ProjectInfoFactory.retrieveProjectReleaseCVE(project);
        releases.values().forEach(release -> projectData.put(release, new HashMap<>()));

    }

    public Map<String, Map<String, VulnerabilityInfo>> gather() throws IOException, ClassNotFoundException {
        Data7 data7 = exporter.loadDataset(project);
        prepareVuln(data7);
        Map<String, Map<String, VulnerabilityInfo>> finalVersion = new LinkedHashMap<>();
        projectData.entrySet().stream().filter(stringListEntry -> stringListEntry.getValue().size() > 10).forEach(stringListEntry -> finalVersion.put(stringListEntry.getKey(), stringListEntry.getValue()));
        exporter.saveProjectVulnData(project, finalVersion);
        return finalVersion;
    }

    private void prepareVuln(Data7 data7) {
        Collection<Vulnerability> vulnds = data7.getVulnerabilitySet().getVulnerabilityDataset().values();
        for (Vulnerability vuln : vulnds) {
            Set<String> versions = vuln.getVersions();
            Map<String, Commit> commits = vuln.getPatchingCommits();
            if (commits.size() > 0) {
                long timestampLast = 0;
                Set<String> files = new HashSet<>();
                for (Object Insidecommits : commits.values()) {
                    for (Commit commit : ((Map < String, Commit >)Insidecommits).values()) {
                        if (commit.getTimestamp() > timestampLast) {
                            timestampLast = commit.getTimestamp();
                        }
                        commit.getFixes().forEach(fileFix -> files.add(fileFix.getFileBefore().getFilePath()));
                    }
                }
                List<String> releases = lookForCorrespondingRelease(timestampLast, versions);
                releases.forEach(release -> {
                    Map<String, VulnerabilityInfo> releaseInfo = projectData.get(release);
                    files.forEach(file -> {
                        if (releaseInfo.containsKey(file)) {
                            releaseInfo.get(file).update(vuln.getCwe(), vuln.getScore(), vuln.getCve(), vuln.getCreationTime());
                        } else {
                            releaseInfo.put(file, new VulnerabilityInfo(vuln.getCwe(), vuln.getScore(), vuln.getCve(), vuln.getCreationTime()));
                        }
                    });

                });

            }
        }
    }

    private List<String> lookForCorrespondingRelease(long timestamp, Set<String> versions) {
        List<String> v = new ArrayList<>();
        Map.Entry<Long, String> release = releases.floorEntry(timestamp);
        if (release != null) {
            v.add(release.getValue());
            for (String version : versions) {
                if (releasesCVE.containsKey(version)) {
                    v.add(releasesCVE.get(version));
                }
            }
        }
        return v;
    }
}
