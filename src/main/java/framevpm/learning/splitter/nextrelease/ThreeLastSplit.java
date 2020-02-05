package framevpm.learning.splitter.nextrelease;

import framevpm.ResourcesPathExtended;
import framevpm.analyze.model.Analysis;
import framevpm.analyze.model.FileAnalysis;
import framevpm.analyze.model.ReleaseAnalysis;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.FileMetaInf;
import framevpm.learning.splitter.ExperimentSplitter;
import framevpm.learning.splitter.fileMeta.VulnerabilityInfo;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ThreeLastSplit extends ReleaseSplitter {

    public final static String NAME = "3LastReleaseGen";

    public ThreeLastSplit(ResourcesPathExtended pathExtended, String project) throws IOException, ClassNotFoundException {
        super(pathExtended, project);
    }

    @Override
    public List<Experiment> generateExperiment() throws IOException, ClassNotFoundException {
        try {
            List<Experiment> experiments = new LinkedList<>();

            boolean loadSavedExperiments = true;
            /*
            if (loadSavedExperiments) {
                int k = 6;
                for (int i = 1; i <= k; i++) {
                    experiments.add(exporter.loadExperiment(project, NAME, i));
                }
                System.out.println("Info | framevpm.learning.splitter.nextrelease.ThreeLastSplit.generateExperiment() | Returning saved experiments.");
                return experiments;
            }
             */
            String currentrelease;

            ReleaseAnalysis currentreleaseanaly;
            int counter = 0;
            LinkedList<LinkedHashMap<FileMetaInf, Map<String, Analysis>>> oldreleases = new LinkedList<>();
            LinkedHashMap<FileMetaInf, Map<String, Analysis>> training;
            LinkedHashMap<FileMetaInf, Map<String, Analysis>> testing;
            int anotherCounter = 1;

            for (Map.Entry<String, Map<String, VulnerabilityInfo>> release : mapVuln.entrySet()) {
                try {
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
                        System.out.println("Processing release | framevpm.learning.splitter.nextrelease.ThreeLastSplit.generateExperiment()" + currentrelease);
                        currentreleaseanaly = exporter.loadReleaseAnalysis(project, currentrelease);

                        testing = new LinkedHashMap<>();

                        for (Map.Entry<String, FileAnalysis> file : currentreleaseanaly.getFileAnalysisMap().entrySet()) {
                            Map<String, Analysis> analysis = file.getValue().getOriginal();
                            VulnerabilityInfo vulnerabilityInfo = release.getValue().getOrDefault(file.getKey(), null);
                            FileMetaInf metaInf = new FileMetaInf(release.getKey(), file.getKey(), file.getValue().getType(), vulnerabilityInfo);
                            testing.put(metaInf, analysis);
                        }
                        if (counter == 3) {
                            training = new LinkedHashMap<>();
//                        training.putAll(oldreleases.get(0));
//                        training.putAll(oldreleases.get(1));
//                        training.putAll(oldreleases.get(2));
                            for (int j = 0; j < counter; j++) {
                                training.putAll(oldreleases.get(j));
                            }
                            //for (LinkedHashMap<FileMetaInf, Map<String, Analysis>> oldRelease : oldreleases) {
                            //    training.putAll(oldRelease);
                            //}
                            Experiment experiment = new Experiment(currentrelease, training, testing);
                            String fileFullName = exporter.saveExperiment(NAME, project, experiment, anotherCounter);
                            experiments.add(new Experiment(experiment.getName(), fileFullName));
                            anotherCounter++;
                            oldreleases.removeFirst();
                            counter--;
                            System.out.println("Another counter | New value " + anotherCounter);
                        }
                        oldreleases.add(testing);
                        counter++;
                    }
                } catch (Exception ex) {
                    System.out.println("Error for AnotherCounter " + anotherCounter + " | framevpm.learning.splitter.nextrelease.ThreeLastSplit.generateExperiment() |");
                    ex.printStackTrace();
                }
            }

            //exporter.saveExperiments(NAME, project, experiments);
            return experiments;
        } catch (Exception ex) {
            System.out.println("Error | framevpm.learning.splitter.nextrelease.ThreeLastSplit.generateExperiment() |");
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
