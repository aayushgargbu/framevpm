package framevpm;

import data7.ResourcesPath;
import framevpm.project.CProjectsInfo;

public class ResourcesPathExtended extends ResourcesPath {


    private final String bugDatasetPath;
    private final String organizeData;
    private final String analysisPath;
    private final String versionPath;
    private final String statPath;
    private final String experimentPath;

    public ResourcesPathExtended(String path) {
        super(path);
        this.bugDatasetPath = getSavingPath() + "bugdatasets/";
        this.organizeData = getSavingPath() + "organizeData/" + CProjectsInfo.focussedVersion + "/";
        this.analysisPath = getSavingPath() + "analysisData/" + CProjectsInfo.focussedVersion + "/";
        this.versionPath = getSavingPath() + "versions/" + CProjectsInfo.focussedVersion + "/";
        this.statPath = getSavingPath() + "stats/" + CProjectsInfo.focussedVersion + "/";
        this.experimentPath = getSavingPath() +"experiments/" + CProjectsInfo.focussedVersion + "/";
    }

    public String getBugDatasetPath() {
        return bugDatasetPath;
    }

    public String getOrganizeData() {
        return organizeData;
    }

    public String getAnalysisPath() {
        return analysisPath;
    }

    public String getVersionPath() {
        return versionPath;
    }

    public String getStatPath() { return statPath; }

    public String getExperimentPath() {
        return experimentPath;
    }
}

