package framevpm.analyze;

import data7.Utils;

import framevpm.ExporterExtended;
import framevpm.ResourcesPathExtended;

import framevpm.analyze.model.ProjectReleaseAnalysed;
import framevpm.organize.model.ProjectData;
import framevpm.project.ProjectInfoFactory;

import miscUtils.Misc;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static framevpm.analyze.Resources.FILTER_FILES_EXTENSION;
import static framevpm.analyze.Resources.FORBIDDEN;
import java.util.LinkedList;

@SuppressWarnings("Duplicates")
public abstract class Analyze {

    protected final String project;
    //protected final GitActions git;
    protected final Collection<String> releases;
    protected final ExporterExtended exporter;
    protected final ProjectData projectData;
    protected final ResourcesPathExtended path;
    protected ProjectReleaseAnalysed projectAnalysis;
    protected String releasePath;
    protected String fixesPath;

    public Analyze(ResourcesPathExtended pathExtended, String project) throws IOException, ClassNotFoundException {
        this.project = project;
        this.path = pathExtended;
        this.exporter = new ExporterExtended(pathExtended);
        TreeMap<Long, String> map = ProjectInfoFactory.retrieveProjectRelease(project);
        if (map == null) {
            throw new RuntimeException("invalid Project");
        }
        this.projectData = exporter.loadProjectData(project);
        if (projectData == null) {
            throw new RuntimeException("missing organized Data from organize package");
        }
        this.releases = map.values();

        //this.releasePath = pathExtended.getVersionPath() + project + "/";
        this.releasePath = pathExtended.getVersionPath();
        this.fixesPath = pathExtended.getCvePath();
        Utils.checkFolderDestination(releasePath);
        if (new File(releasePath).list().length != releases.size()) {
            downloadAllVersionFor(project);
        }
        this.projectAnalysis = exporter.loadProjectReleaseAnalysis(project);
        if (projectAnalysis == null) {
            projectAnalysis = new ProjectReleaseAnalysed(project);
            exporter.saveProjectReleaseAnalysis(projectAnalysis);
        }
    }

    private void downloadAllVersionFor(String project) {
        String github = ProjectInfoFactory.retrieveProjectGithub(project);
        for (String version : releases) {
            String file = releasePath + version + ".tar.gz";
            if (!new File(file).exists()) {
                Misc.downloadFromURL(github + "/archive/" + version + ".tar.gz", releasePath);
            }
        }
    }

    protected Map<String, String> loadVersion(String version) {
        Map<String, String> fileMap = new HashMap<>();
        try {
            TarArchiveInputStream tar = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(releasePath + version + ".tar.gz")));
            TarArchiveEntry currentEntry = tar.getNextTarEntry();
            BufferedReader br;
            while (currentEntry != null) {
                br = new BufferedReader(new InputStreamReader(tar)); // Read directly from tarInput
                String file = currentEntry.getName();
                int index = file.indexOf("/");
                if (index > 0) {
                    file = file.substring(index + 1);
                }
                if (file.matches(FILTER_FILES_EXTENSION) && !file.contains(FORBIDDEN)) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    fileMap.put(file, sb.toString());
                }
                currentEntry = tar.getNextTarEntry(); // You forgot to iterate to the next file
            }
        } catch (IOException e) {
            System.err.println("problem with tar");
        }
        return fileMap;
    }

    protected Map<String, String> loadVersion(String project, String version) {
        Map<String, String> fileMap = new HashMap<>();
        try {
            TarArchiveInputStream tar = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(releasePath + version + ".tar.gz")));
            TarArchiveEntry currentEntry = tar.getNextTarEntry();
            BufferedReader br;
            while (currentEntry != null) {
                br = new BufferedReader(new InputStreamReader(tar)); // Read directly from tarInput
                String file = currentEntry.getName();
                int index = file.indexOf("/");
                if (index > 0) {
                    file = file.substring(index + 1);
                }
                if (file.matches(FILTER_FILES_EXTENSION) && !file.contains(FORBIDDEN)) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    fileMap.put(file, sb.toString());
                }
                currentEntry = tar.getNextTarEntry(); // You forgot to iterate to the next file
            }
            Map<String, String> mapFixes = loadFixes(project, version);
            int countFixes = 0;
            for (String key : mapFixes.keySet()) {
                fileMap.put(key, mapFixes.get(key));
                countFixes++;
            }
            System.out.println("Added " + countFixes + " fixes for " + project + ", version " + version);
        } catch (IOException e) {
            System.err.println("problem with tar");
        }
        return fileMap;
    }

    Map<String, String> loadFixes(String project, String version) {
        System.out.println("Loading fixes for " + project + ", version " + version);
        Map<String, String> returnMap = new HashMap();
        try {
            String versionFixesPath = fixesPath + "/" + project + "/" + version;
            if (FileExists(versionFixesPath)) {
                File versionFixesDirectory = new File(versionFixesPath);
                for (File cveDirectory : versionFixesDirectory.listFiles()) {
                    if (cveDirectory.isDirectory()) {
                        for (File aftercodeFile : cveDirectory.listFiles()) {
                            String aftercodeFileName = aftercodeFile.getName();
                            if (aftercodeFileName.contains("_After.c")) {
                                String key = cveDirectory.getName() + "_" + aftercodeFileName;
                                String value = ConvertListToString(ReadFileToList(aftercodeFile.getPath()));
                                returnMap.put(key, value);
                            }
                        }
                    }
                }
            }
            System.out.println("Loaded " + returnMap.size() + " fixes for " + project + ", version " + version);
        } catch (Exception ex) {
            System.err.println("Problem loading the fixes for " + project + ", version " + version);
            ex.printStackTrace();
        }
        return returnMap;
    }

    public Boolean FileExists(String filepath) throws Exception {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.err.println("Error | Analyze.FileExists()");
            throw ex;
        }
    }

    public LinkedList<String> ReadFileToList(String filepath) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            LinkedList list = new LinkedList();
            String line = null;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            if (list.isEmpty()) {
                list = null;
            }
            return list;
        } catch (Exception ex) {
            System.err.println("Error | Analyze.ReadFileToList()");
            throw ex;
        }
    }

    String ConvertListToString(LinkedList<String> list) throws Exception {
        try {
            String returnString = "";
            for (String str : list) {
                if (returnString.isEmpty()) {
                    returnString = str;
                } else {
                    returnString += "\\r\\n" + str;
                }
            }
            return returnString;
        } catch (Exception ex) {
            System.err.println("Error | Analyze.ConvertListToString()");
            throw ex;
        }
    }

    public abstract ProjectReleaseAnalysed processFeatures() throws IOException;

    public abstract String getApproachName();

}
