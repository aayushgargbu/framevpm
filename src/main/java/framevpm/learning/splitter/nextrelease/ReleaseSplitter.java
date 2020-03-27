package framevpm.learning.splitter.nextrelease;

import framevpm.ResourcesPathExtended;
import framevpm.analyze.model.Analysis;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.FileMetaInf;
import framevpm.learning.splitter.ExperimentSplitter;
import framevpm.learning.splitter.fileMeta.VulnerabilityInfo;
import framevpm.organize.model.FileType;
import framevpm.project.ProjectInfoFactory;

import java.io.IOException;
import java.util.*;

public abstract class ReleaseSplitter extends ExperimentSplitter {

    public final static String NAME = "nextReleaseGenRealistic";

    public ReleaseSplitter(ResourcesPathExtended pathExtended, String project) throws IOException, ClassNotFoundException {
        super(pathExtended, project);
    }

    public List<Experiment> generateRealisticExperiment(List<Experiment> experiments) {
        Map<String, Long> releaseTime = new HashMap<>();
        ProjectInfoFactory.retrieveProjectRelease(project).forEach((time, release) -> releaseTime.put(release, time));
        List<Experiment> experimentList = new ArrayList<>(experiments.size());
        int anotherCounter = 1;
        for (Experiment ex : experiments) {
            try {
                Experiment experiment = ex.loadExperiment(ex.getFullFileName());
                int[] nbVuln = {0};
                long releaseT = releaseTime.get(experiment.getName());
                LinkedHashMap<FileMetaInf, Map<String, Analysis>> training = new LinkedHashMap<>();
                LinkedHashMap<FileMetaInf, Map<String, Analysis>> trainingInfo = experiment.getTraining();
                for (FileMetaInf fileMetaInf : trainingInfo.keySet()) {
                    Map<String, Analysis> stringAnalysisMap = trainingInfo.get(fileMetaInf);
                    FileMetaInf metaInf = fileMetaInf;
                    if (fileMetaInf.getType() == FileType.Vulnerability) {
                        if (fileMetaInf.getVulnerabilityInfo() != null) {
                            if (fileMetaInf.getVulnerabilityInfo().getTimestampDiscover() > releaseT) {
                                metaInf = new FileMetaInf(fileMetaInf.getRelease(), fileMetaInf.getFile(), FileType.Clear, null);
                            }
                            /*
                            else {
                                nbVuln[0]++;
                            }
                             */
                        }
                        /*
                        else {
                            int l = 0;
                        }
                         */
                    }
                    training.put(metaInf, stringAnalysisMap);
                }
                /*
                experiment.getTraining().forEach(((fileMetaInf, stringAnalysisMap) -> {
                }));
                 */
                /*
                if (nbVuln[0] > 10) 
                {
                 */
                Experiment experiment1 = new Experiment(experiment.getName(), training, experiment.getTesting());
                String fileFullName = exporter.saveExperiment(NAME, project, experiment1, anotherCounter);
                experimentList.add(new Experiment(experiment1.getName(), fileFullName));
                //experimentList.add(experiment1);
                anotherCounter++;
                /*
                }
                 */
            } catch (Exception exc) {
                System.out.println("Error | framevpm.learning.splitter.nextrelease.ReleaseSplitter.generateRealisticExperiment() | Skipping");
                exc.printStackTrace();
            }
        }
        /*
        experiments.forEach(experiment -> {
        });
         */
        return experimentList;
    }
}
