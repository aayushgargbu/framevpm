package framevpm.learning.splitter.nextrelease;

import framevpm.ResourcesPathExtended;
import framevpm.analyze.model.Analysis;
import framevpm.analyze.model.FileAnalysis;
import framevpm.analyze.model.ReleaseAnalysis;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.FileMetaInf;
import framevpm.learning.splitter.ExperimentSplitter;
import framevpm.learning.splitter.fileMeta.VulnerabilityInfo;
import framevpm.organize.model.FileType;
import framevpm.project.ProjectInfoFactory;

import java.io.IOException;
import java.util.*;

public class GeneralSplit extends ReleaseSplitter {

    public final static String NAME = "nextReleaseGen";

    public GeneralSplit(ResourcesPathExtended pathExtended, String project) throws IOException, ClassNotFoundException {
        super(pathExtended, project);
    }

    @Override
    public List<Experiment> generateExperiment() throws IOException, ClassNotFoundException {
        try {
            List<Experiment> experiments = new LinkedList<>();

            boolean loadSavedExperiments = true;
            /*
            if (loadSavedExperiments) {
                int k = 63;
                for (int i = 1; i <= k; i++) {
                    experiments.add(exporter.loadExperiment(project, NAME, i));
                }
                System.out.println("Info | framevpm.learning.splitter.nextrelease.GeneralSplit.generateExperiment() | Returning saved experiments.");
                return experiments;
            }
             */
            String currentrelease;
            String oldrelease = null;
            ReleaseAnalysis currentreleaseanaly;

            LinkedHashMap<FileMetaInf, Map<String, Analysis>> training = null;
            LinkedHashMap<FileMetaInf, Map<String, Analysis>> testing;
            int anotherCounter = 1;

            for (Map.Entry<String, Map<String, VulnerabilityInfo>> release : mapVuln.entrySet()) {
                if (release.getValue().size() > 0) { //&& anotherCounter <= 20) {
                    currentrelease = release.getKey();
                    if (loadSavedExperiments) {
                        Experiment savedExperiment = exporter.CheckExperimentExists(project, NAME, anotherCounter, currentrelease);
                        if (savedExperiment != null) {
                            experiments.add(savedExperiment);
                            anotherCounter++;
                            System.out.println("Another counter | New value " + anotherCounter);
                            continue;
                        }
                    }
                    System.out.println("Processing release | framevpm.learning.splitter.nextrelease.GeneralSplit.generateExperiment()" + currentrelease);
                    currentreleaseanaly = exporter.loadReleaseAnalysis(project, currentrelease);

                    testing = new LinkedHashMap<>();

                    for (Map.Entry<String, FileAnalysis> file : currentreleaseanaly.getFileAnalysisMap().entrySet()) {
                        Map<String, Analysis> analysis = file.getValue().getOriginal();
                        VulnerabilityInfo vulnerabilityInfo = release.getValue().getOrDefault(file.getKey(), null);
                        FileMetaInf metaInf = new FileMetaInf(release.getKey(), file.getKey(), file.getValue().getType(), vulnerabilityInfo);
                        testing.put(metaInf, analysis);
                    }
                    if (training != null) {
                        Experiment experiment = new Experiment(currentrelease, training, testing);
                        String fileFullName = exporter.saveExperiment(NAME, project, experiment, anotherCounter);
                        experiments.add(new Experiment(experiment.getName(), fileFullName));
                        //experiments.add(experiment);
                        anotherCounter++;
                        System.out.println("Another counter | New value " + anotherCounter);
                    }
                    training = testing;
                }
            }

            //exporter.saveExperiments(NAME, project, experiments);
            return experiments;
        } catch (Exception ex) {
            System.out.println("Error | framevpm.learning.splitter.nextrelease.GeneralSplit.generateExperiment() |");
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
