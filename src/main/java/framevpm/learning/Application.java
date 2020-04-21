package framevpm.learning;

import data7.project.CProjects;
import data7.project.Project;
import framevpm.ExporterExtended;
import framevpm.ResourcesPathExtended;
import framevpm.learning.approaches.Approach;
import framevpm.learning.approaches.codeMetrics.CodeMetricsApproach;
import framevpm.learning.approaches.ifc.FunctionCallsApproach;
import framevpm.learning.approaches.ifc.IncludesApproach;
import framevpm.learning.approaches.naturalness.NaturalnessAndCM;
import framevpm.learning.approaches.naturalness.PureNaturalness;
import framevpm.learning.approaches.textmining.BagOfWordsApproach;
import framevpm.learning.model.ApproachResult;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.classmodel.BugVul;
import framevpm.learning.model.classmodel.ClassModel;
import framevpm.learning.model.classmodel.VulBugClear;
import framevpm.learning.model.classmodel.VulNotVul;
import framevpm.learning.splitter.ExperimentSplitter;
import framevpm.learning.splitter.nextrelease.GeneralSplit;
import framevpm.learning.splitter.nextrelease.ReleaseSplitter;
import framevpm.learning.splitter.nextrelease.ThreeLastSplit;

import java.io.IOException;
import java.util.List;

import static framevpm.learning.Classifiers.getClassifiers;

public class Application {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //"/Users/matthieu/Desktop/data7/"
        if (args.length == 1) {
            ResourcesPathExtended pathExtended = new ResourcesPathExtended(args[0]);
            ExporterExtended exporterExtended = new ExporterExtended(pathExtended);
            CSVExporter csvExporter = new CSVExporter(pathExtended, "wireshark");
            Project[] projects = new Project[]{
                    CProjects.OPEN_SSL,
                    CProjects.WIRESHARK,
                    CProjects.LINUX_KERNEL
            };

            ClassModel[] classModels = new ClassModel[]{
                    new VulNotVul(),
                    new BugVul(),
                    new VulBugClear()
            };

            for (Project project : projects) {
                System.out.println("|Starting Project: " + project.getName());
                for (ClassModel model : classModels) {
                    System.out.println("|    Starting Class Model: " + model.getName());
                    ReleaseSplitter[] experimentSplitters = {
                            new GeneralSplit(pathExtended, project.getName()),
                            new ThreeLastSplit(pathExtended, project.getName())
                    };


                    for (ReleaseSplitter experimentSplitter : experimentSplitters) {
                        System.out.println("|        Starting Experiment: " + experimentSplitter.getName());
                        List<Experiment>[] exp = new List[2];
                        exp[0] = new ExporterExtended(pathExtended).loadExperiments(project.getName(), experimentSplitter.getName());

                        if (exp[0] == null) {
                            exp[0] = experimentSplitter.generateExperiment();
                        }

                        exp[1] = experimentSplitter.generateRealisticExperiment(exp[0]);
                        boolean realistic = false;
                        for (List<Experiment> experimentList : exp) {
                            Approach[] approaches = {
                                    new NaturalnessAndCM(experimentList, model),
                                    new PureNaturalness(experimentList, model),
                                    new CodeMetricsApproach(experimentList, model),
                                    new IncludesApproach(experimentList, model),
                                    new FunctionCallsApproach(experimentList, model),
                                    new BagOfWordsApproach(experimentList, model)
                            };

                            for (Approach approach : approaches) {
                                System.out.println("|            Starting Approach: " + approach.getApproachName());
                                for (String classifier : getClassifiers()) {
                                    System.out.println("|                Starting Classifier: " + classifier);
                                    approach.prepareInstances();
                                    runwithSmote(exporterExtended, csvExporter, project, model, experimentSplitter, approach, classifier, realistic, true);
                                    System.out.println("|                    1/2");
                                    runwithSmote(exporterExtended, csvExporter, project, model, experimentSplitter, approach, classifier, realistic, false);
                                    System.out.println("|                    2/2");
                                }
                            }
                            realistic = true;
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("Not enough arguments");
        }

    }

    private static void runwithSmote(ExporterExtended exporterExtended, CSVExporter csvExporter, Project project, ClassModel model, ExperimentSplitter experimentSplitter, Approach approach, String classifier, boolean realistic, boolean smote) throws IOException {
        ApproachResult result = approach.runWith(classifier, smote);
        exporterExtended.saveApproachResult(project.getName(), experimentSplitter.getName(), model.getName(), realistic, result);
        csvExporter.exportResultToCSV(project.getName(), experimentSplitter.getName(), model, realistic, result);

    }
}
