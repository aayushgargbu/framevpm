package framevpm.analyze.approaches.filebyfile;

import data7.project.ProjectFactory;
import diff.DeltaHistory;
import framevpm.ResourcesPathExtended;
import framevpm.analyze.approaches.PerFileAnalysis;
import framevpm.analyze.model.Analysis;
import gitUtilitaries.GitActions;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeChurnDeveloperMetrics extends PerFileAnalysis {

    public final static String NAME = "Code Churn Developers";

    private GitActions git;

    public CodeChurnDeveloperMetrics(ResourcesPathExtended pathExtended, String project) throws IOException, ClassNotFoundException {
        super(pathExtended, project);
        try {
            this.git = new GitActions(ProjectFactory.retrieveProjectInfo(project).getOnlineRepository(), path.getGitPath() + project);
        } catch (Exception ex) {
            System.out.println("Error | framevpm.analyze.approaches.filebyfile.CodeChurnDeveloperMetrics.<init>() | " + ex.getStackTrace().toString());
        }
    }

    @Override
    public Analysis analyseFile(String file, String fileContent, String hash) {
        Map<String, Serializable> objectMap = new HashMap<>();
        List<GitActions.NamedCommit> listofCommit = git.listOfCommitImpactingAFile(file, hash);
        DeltaHistory delta;
        GitActions.DeveloperHistory dev;
        if (listofCommit != null) {
            delta = git.getDeltaFileFromList(listofCommit);
            dev = git.getDevHistoryOfAFileFromList(listofCommit);
        } else {
            delta = new DeltaHistory(0, 0, 0);
            dev = new GitActions.DeveloperHistory(0, 0, 0);
        }

        objectMap.put("totalNumberOfDeveloper", dev.getTotalNumberOfDeveloper());
        objectMap.put("currentNumberOfDeveloper", dev.getCurrentNumberOfDeveloper());
        objectMap.put("numberOfChanges", dev.getNumberOfChanges());

        objectMap.put("linesAdded", delta.getLinesAdded());
        objectMap.put("linesDeleted", delta.getLinesDeleted());
        objectMap.put("linesModified", delta.getLinesModified());

        return new Analysis(objectMap);
    }

    @Override
    public String getApproachName() {
        return NAME;
    }
}
