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
    private final String fixesPath;

    public ResourcesPathExtended(String path) {
        super(path);
        this.bugDatasetPath = getSavingPath() + "bugdatasets/";
        this.organizeData = getSavingPath() + "organizeData/" + CProjectsInfo.focussedVersion + "/";
        this.analysisPath = getSavingPath() + "analysisData/" + CProjectsInfo.focussedVersion + "/";
        this.versionPath = getSavingPath() + "versions/" + CProjectsInfo.focussedVersion + "/";
        this.statPath = getSavingPath() + "stats/" + CProjectsInfo.focussedVersion + "/";
        this.experimentPath = getSavingPath() + "experiments/" + CProjectsInfo.focussedVersion + "/";
        this.fixesPath = getCvePath();
    }

    public ResourcesPathExtended(String path, String projectName, Boolean realistic) {
        super(path);
        String idealOrRealistic = realistic == true ? "realistic" : "ideal";
        this.bugDatasetPath = getSavingPath() + "bugdatasets/" + idealOrRealistic + "/" + projectName + "/" + CProjectsInfo.focussedVersion + "/";
        this.organizeData = getSavingPath() + "organizeData/" + idealOrRealistic + "/" + projectName + "/" + CProjectsInfo.focussedVersion + "/";
        this.analysisPath = getSavingPath() + "analysisData/" + idealOrRealistic + "/" + projectName + "/" + CProjectsInfo.focussedVersion + "/";
        this.versionPath = getSavingPath() + "versions/" + idealOrRealistic + "/" + projectName + "/" + CProjectsInfo.focussedVersion + "/";
        this.statPath = getSavingPath() + "stats/" + idealOrRealistic + "/" + projectName + "/" + CProjectsInfo.focussedVersion + "/";
        this.experimentPath = getSavingPath() + "experiments/" + idealOrRealistic + "/" + projectName + "/" + CProjectsInfo.focussedVersion + "/";
        this.fixesPath = getCvePath();
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

    public String getStatPath() {
        return statPath;
    }

    public String getExperimentPath() {
        return experimentPath;
    }

    public String getFixesPath() {
        return fixesPath;
    }
}
