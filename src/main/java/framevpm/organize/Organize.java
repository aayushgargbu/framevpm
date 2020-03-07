package framevpm.organize;

import data7.model.Data7;
import data7.model.change.Commit;
import data7.model.change.FileFix;
import data7.model.vulnerability.Vulnerability;
import data7.project.CProjects;
import data7.project.ProjectFactory;
import framevpm.ExporterExtended;
import framevpm.ResourcesPathExtended;
import framevpm.bugcollector.model.BugDataset;
import framevpm.organize.model.*;
import framevpm.project.ProjectInfoFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 *
 */
public class Organize {

    private final String project;
    private final ProjectData projectData;
    private final Set<String> commitMessageFiltering;
    private final Set<String> vulnerabilityHash;
    private final TreeMap<Long, String> releases;
    private final Map<String, String> releasesCVE;
    private final ResourcesPathExtended resourcesPathExtended;
    private final ExporterExtended exporter;

    public Organize(ResourcesPathExtended resourcesPathExtended, String project) {
        this.resourcesPathExtended = resourcesPathExtended;
        this.exporter = new ExporterExtended(resourcesPathExtended);
        this.project = project;
        if (ProjectFactory.retrieveProjectInfo(project) == null) {
            throw new RuntimeException("Incorrect Project");
        }
        this.projectData = new ProjectData(project);
        this.commitMessageFiltering = new HashSet<>();
        releases = ProjectInfoFactory.retrieveProjectRelease(project);
        releasesCVE = ProjectInfoFactory.retrieveProjectReleaseCVE(project);
        vulnerabilityHash = new HashSet<>();
    }

    public ProjectData balance(boolean bugs) throws IOException, ClassNotFoundException, ParseException {
        System.out.println("Starting processing: " + project);
        Data7 data7 = exporter.loadDataset(project);
        prepareVuln(data7);
        if (bugs) {
            prepareBug(data7);
        }
        propagate();
        exporter.saveProjectData(projectData);
        return projectData;
    }

    private void prepareVuln(Data7 data7) {
        int count = 0;
        Collection<Vulnerability> vulnds = data7.getVulnerabilitySet().getVulnerabilityDataset().values();
        for (Vulnerability vuln : vulnds) {
            try {
                Set<String> versions = vuln.getVersions();
                Map<String, Commit> commits = vuln.getPatchingCommits();
                if (commits.size() == 1) {
                    count += handleSingleCommit(commits, vuln.getCwe(), versions, vuln.getScore());
                } else if (commits.size() > 1) {
                    count += handleDouble(commits, vuln.getCwe(), versions, vuln.getScore());
                }
            } catch (Exception ex) {
                System.out.println("Error processing vulnerability CVE " + vuln.getCve() + ", CWE " + vuln.getCwe() + " | framevpm.organize.Organize.prepareVuln() |");
                ex.printStackTrace();
            }
        }
        projectData.setVulndone(true);
        System.out.println("number of Vulnerable File: " + count);
    }

    private void prepareBug(Data7 data7) throws IOException, ClassNotFoundException {
        final int[] count = {0};
        BugDataset bugDataset = exporter.loadBugDataset(data7.getProject().getName());
        Set<Map.Entry<String, Commit>> bugds = bugDataset.getDataset().entrySet();

        for (Map.Entry<String, Commit> bug : bugds) {
            if (!vulnerabilityHash.contains(bug.getKey()) && commitMessageFiltering.add(bug.getValue().getMessage().toLowerCase())) {
                Map.Entry<Long, String> release = releases.floorEntry(bug.getValue().getTimestamp());
                if (release != null) {
                    String rel = release.getValue();
                    ReleaseData releaseData = projectData.getOrCreateRelease(rel);
                    bug.getValue().getFixes().forEach(fileFix -> {
                        if (vulnerabilityHash.add(fileFix.getOldHash())) {
                            String filename = fileFix.getFileBefore().getFilePath();
                            FileData fileExp = releaseData.getOrCreateFile(filename);
                            if (fileExp.getTypeFile() != FileType.Vulnerability) {
                                fileExp.setTypeFile(FileType.Buggy);
                            }
                            String after = fileFix.getFileAfter().getFileContent();
                            String before = fileFix.getFileBefore().getFileContent();
                            FixData fix = new FixData(FileType.Buggy, fileFix.getOldHash(), bug.getValue().getHash(), before, after);
                            fileExp.getFixes().add(fix);
                            count[0]++;
                        }
                    });
                }
            }
        }
        System.out.println("number of Bugs File: " + count[0]);
        projectData.setBugdone(true);
    }

    private void propagate() {
        try {
            Map<String, FileType> history = new HashMap<>();
            final int[] count3 = {0};
            releases.forEach((time, release) -> {
                ReleaseData releaseExp = projectData.getOrCreateRelease(release);
                releaseExp.getFileMap().entrySet().forEach(
                        stringFileExpEntry -> {
                            if (history.containsKey(stringFileExpEntry.getKey())) {
                                if (stringFileExpEntry.getValue().getTypeFile() == FileType.Vulnerability && history.get(stringFileExpEntry.getKey()) != FileType.Vulnerability) {
                                    history.put(stringFileExpEntry.getKey(), FileType.Vulnerability);
                                    count3[0]++;
                                }
                            } else {
                                history.put(stringFileExpEntry.getKey(), stringFileExpEntry.getValue().getTypeFile());
                            }
                        }
                );
                for (Map.Entry<String, FileType> hist : history.entrySet()) {
                    FileData fileExp = releaseExp.getOrCreateFile(hist.getKey());
                    if (fileExp.getTypeFile() == FileType.Clear) {
                        if (hist.getValue() == FileType.Buggy) {
                            fileExp.setTypeFile(FileType.BuggyHistory);
                        } else if (hist.getValue() == FileType.Vulnerability) {
                            fileExp.setTypeFile(FileType.VulnerableHistory);
                        }
                    }
                }
            });

            long count = history.entrySet().stream().map(Map.Entry::getValue).filter(type -> type == FileType.Vulnerability).count();
            long count2 = history.entrySet().stream().map(Map.Entry::getValue).filter(type -> type == FileType.Buggy).count();
            System.out.println("number of Unique Vulnerable File: " + count + " among them " + count3[0] + " were once declared as Buggy before");
            System.out.println("number of Unique Buggy File: " + count2);
        } catch (Exception ex) {
            System.out.println("Error | framevpm.organize.Organize.propagate() |");
            ex.printStackTrace();
        }
    }

    private int handleSingleCommit(Map<String, Commit> commits, String cwe, Set<String> versions, String cvss) {
        try {
            final int[] count = {0};
            Commit comm = null;
            for (Object obj : commits.values()) {
                Map<String, Commit> probCommits = (Map<String, Commit>) obj;
                if(probCommits != null){
                    if(probCommits.isEmpty() == false){
                        comm = new ArrayList<>(probCommits.values()).get(0);
                        break;
                    }
                }
            }

            Commit commit = comm; //new Commit(hash, message, timestamp, fixes);
            vulnerabilityHash.add(commit.getHash());

            if (commitMessageFiltering.add(commit.getMessage().toLowerCase())) {
                List<String> releases = lookForCorrespondingRelease(commit.getTimestamp(), versions);
                if (releases.size() > 0) {
                    String closestrelease = releases.get(0);
                    ReleaseData closestReleaseData = projectData.getOrCreateRelease(closestrelease);
                    List<ReleaseData> releaseDataList = new ArrayList<>();
                    for (int i = 1; i < releases.size(); i++) {
                        releaseDataList.add(projectData.getOrCreateRelease(releases.get(i)));
                    }
                    commit.getFixes().forEach(fileFix -> {
                        count[0]++;
                        String filename = fileFix.getFileBefore().getFilePath();
                        for (ReleaseData releaseData : releaseDataList) {
                            FileData fileData = releaseData.getOrCreateFile(filename);
                            fileData.setTypeFile(FileType.Vulnerability);
                        }

                        FileData fileData = closestReleaseData.getOrCreateFile(filename);
                        fileData.setTypeFile(FileType.Vulnerability);
                        String after = fileFix.getFileAfter().getFileContent();
                        String before = fileFix.getFileBefore().getFileContent();
                        FixData fix = new FixData(FileType.Vulnerability, fileFix.getOldHash(), commit.getHash(), before, after);
                        fix.setCwe(cwe);
                        fix.setCvss(cvss);
                        fileData.getFixes().add(fix);
                    });
                }
            }
            return count[0];
        } catch (Exception ex) {
            System.out.println("Error for cwe " + cwe + ", cvss " + cvss + " | framevpm.organize.Organize.handleSingleCommit() |");
            ex.printStackTrace();
            return 0;
        }
    }

    private int handleDouble(Map<String, Commit> commits, String cwe, Set<String> versions, String cvss) {
        try {
            final int[] count = {0};
            TreeMap<Long, Commit> order = new TreeMap<>();
            commits.values().forEach((commit) -> {
                order.put(commit.getTimestamp(), commit);
                vulnerabilityHash.add(commit.getHash());
            });
            Map<String, Set<String>> fileDoneforHash = new HashMap<>();

            order.descendingMap().forEach((time, commit) -> {
                if (commitMessageFiltering.add(commit.getMessage().toLowerCase())) {
                    List<String> releases = lookForCorrespondingRelease(commit.getTimestamp(), versions);
                    if (releases.size() > 0) {
                        String closestrelease = releases.get(0);
                        ReleaseData closestReleaseData = projectData.getOrCreateRelease(closestrelease);
                        List<ReleaseData> releaseDataList = new ArrayList<>();
                        for (int i = 1; i < releases.size(); i++) {
                            releaseDataList.add(projectData.getOrCreateRelease(releases.get(i)));
                        }
                        commit.getFixes().forEach(fileFix -> {
                            String filename = fileFix.getFileBefore().getFilePath();
                            for (ReleaseData releaseData : releaseDataList) {
                                FileData fileData = releaseData.getOrCreateFile(filename);
                                fileData.setTypeFile(FileType.Vulnerability);
                            }

                            FileData fileData = closestReleaseData.getOrCreateFile(filename);
                            fileData.setTypeFile(FileType.Vulnerability);

                            String after = fileFix.getFileAfter().getFileContent();
                            final String[] before = new String[1];
                            final boolean[] ok = {false};
                            if (commits.containsKey(fileFix.getOldHash())) {
                                //merge files;
                                Commit committoMerge = commits.get(fileFix.getOldHash());

                                committoMerge.getFixes().forEach(otherFiler -> {
                                    if (otherFiler.getFileAfter().getFilePath().equals(filename)) {

                                        if (fileDoneforHash.containsKey(committoMerge.getHash())) {
                                            fileDoneforHash.get(committoMerge.getHash()).add(filename);
                                        } else {
                                            Set<String> list = new HashSet<>();
                                            list.add(filename);
                                            fileDoneforHash.put(committoMerge.getHash(), list);
                                        }
                                        before[0] = otherFiler.getFileBefore().getFileContent();
                                        ok[0] = true;
                                    }
                                });

                            } else {
                                if (!(fileDoneforHash.containsKey(commit.getHash()) && fileDoneforHash.get(commit.getHash()).contains(filename))) {
                                    before[0] = fileFix.getFileBefore().getFileContent();
                                    ok[0] = true;
                                }
                            }

                            if (ok[0]) {
                                count[0]++;
                                FixData fix = new FixData(FileType.Vulnerability, fileFix.getOldHash(), commit.getHash(), before[0], after);
                                fix.setCwe(cwe);
                                fix.setCvss(cvss);
                                fileData.getFixes().add(fix);
                            }
                        });
                    }
                }
            });
            return count[0];
        } catch (Exception ex) {
            System.out.println("Error for cwe " + cwe + ", cvss " + cvss + " | framevpm.organize.Organize.handleDouble() |");
            ex.printStackTrace();
            return 0;
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

    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {
        long time = System.currentTimeMillis();
        ResourcesPathExtended path = new ResourcesPathExtended("/Users/matthieu/Desktop/data7/");
        System.out.println("Start Linux");
        Organize organize = new Organize(path, CProjects.LINUX_KERNEL.getName());
        organize.balance(true);
        System.out.println("End Linux : " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        System.out.println("Start SystemD");
        organize = new Organize(path, CProjects.SYSTEMD.getName());
        organize.balance(true);
        System.out.println("End SystemD : " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        System.out.println("Start Wireshark");
        organize = new Organize(path, CProjects.WIRESHARK.getName());
        organize.balance(true);
        System.out.println("End Wireshark : " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        System.out.println("Start SSL");
        organize = new Organize(path, CProjects.OPEN_SSL.getName());
        organize.balance(true);
        System.out.println("End SSL : " + (System.currentTimeMillis() - time));
    }

}
