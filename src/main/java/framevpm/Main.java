package framevpm;

import data7.Importer;
import data7.project.CProjects;
import data7.project.Project;
import framevpm.analyze.Application;
import framevpm.analyze.approaches.filebyfile.SimpleCodeMetrics;
import framevpm.bugcollector.BugCollector;
import framevpm.learning.CSVExporter;
import framevpm.learning.approaches.Approach;
import framevpm.learning.approaches.codeMetrics.CodeMetricsApproach;
import framevpm.learning.approaches.ifc.FunctionCallsApproach;
import framevpm.learning.approaches.ifc.IncludesApproach;
import framevpm.learning.approaches.naturalness.NaturalnessAndCM;
import framevpm.learning.approaches.naturalness.PureNaturalness;
import framevpm.learning.approaches.textmining.BagOfWordsApproach;
import framevpm.learning.model.ApproachResult;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.classmodel.ClassModel;
import framevpm.learning.model.classmodel.VulNotVul;
import framevpm.learning.splitter.nextrelease.GeneralSplit;
import framevpm.learning.splitter.nextrelease.ReleaseSplitter;
import framevpm.learning.splitter.nextrelease.ThreeLastSplit;
import framevpm.organize.Organize;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import framevpm.project.CProjectsInfo;

public class Main {

    public static void main(String[] args) {
        try {
            String repositoryFullPath = "";
            String projectName = "";
            String realisticString = "";
            
            if (args.length == 0) {
                repositoryFullPath = "C:/GitHub/framevpm/repository/";
                projectName = "linux_kernel";
                realisticString = "false";
            } else {
                repositoryFullPath = args[0];
                projectName = args[1];
                realisticString = args[2];
            }
            
            boolean realistic = Boolean.getBoolean(realisticString);
            boolean bugs = false;
            boolean smote = realistic == true ? false : true;

            Project project = null;
            String[] focussedVersions = null;
            switch (projectName) {
                case "linux_kernel":
                    project = CProjects.LINUX_KERNEL;
                    focussedVersions = CProjectsInfo.focussedLinuxKernelVersions;
                    break;
                case "wireshark":
                    project = CProjects.WIRESHARK;
                    focussedVersions = CProjectsInfo.focussedWiresharkVersions;
                    break;
                case "openssl":
                    project = CProjects.OPEN_SSL;
                    focussedVersions = CProjectsInfo.focussedOpenSSLVersions;
                    break;
            }

            for (String focussedVersion : focussedVersions) {
                CProjectsInfo.focussedVersion = focussedVersion;
                //ResourcesPathExtended pathExtended = new ResourcesPathExtended(repositoryFullPath); //("/home/matthieu/vpm/");
                ResourcesPathExtended pathExtended = new ResourcesPathExtended(repositoryFullPath, projectName, realistic);

                //Training
                ///*
                new Importer(pathExtended).updateOrCreateDatasetFor(project);
                System.gc();
                //new BugCollector(pathExtended).updateOrCreateBugDataset(project.getName());
                //System.gc();
                new Organize(pathExtended, project.getName()).balance(bugs);
                System.gc();
                new Application(pathExtended, project.getName()).runAll();
                System.gc();
                //*/
                //Testing
                ///*
                ClassModel classModel = new VulNotVul();
                //ReleaseSplitter experimentSplitter = new GeneralSplit(pathExtended, project.getName());
                ReleaseSplitter experimentSplitter = new ThreeLastSplit(pathExtended, project.getName());
                List<Experiment> experimentList = experimentSplitter.generateExperiment();
                if (realistic) {
                    experimentList = experimentSplitter.generateRealisticExperiment(experimentList);
                }
                System.out.println("Info | framevpm.Main.main() | Finished generating experiments");
                Approach[] approaches = {
                    /*new NaturalnessAndCM(experimentList, model),
                        new PureNaturalness(experimentList, model),*/
                    new CodeMetricsApproach(experimentList, classModel),
                    new IncludesApproach(experimentList, classModel),
                    new FunctionCallsApproach(experimentList, classModel),
                    new BagOfWordsApproach(experimentList, classModel)
                };
                System.out.println("Info | framevpm.Main.main() | Finished creating approaches");
                for (Approach approach : approaches) {
                    try {
                        approach.prepareInstances();
                        System.out.println("Info | framevpm.Main.main() | Finished preparing instances for " + approach.getApproachName());

                        LinkedList<String> classifierNames = new LinkedList();
                        classifierNames.add("RandomForest");
                        /*classifierNames.add("Logistic");
                            classifierNames.add("J48");
                            classifierNames.add("Ada");
                            classifierNames.add("SVM");
                            classifierNames.add("KNear");
                            classifierNames.add("MLPerceptron");*/

                        for (String classifierName : classifierNames) {
                            ApproachResult result = approach.runWith(classifierName, smote);
                            System.out.println("Info | framevpm.Main.main() | Finished getting approach results from " + approach.getApproachName());
                            /*ExporterExtended exporterExtended = new ExporterExtended(pathExtended);
                                exporterExtended.saveApproachResult(project.getName(), experimentSplitter.getName(), classModel.getName(), true, result);
                                System.out.println("Info | framevpm.Main.main() | Finished saving approach results from " + approach.getApproachName());*/
                            CSVExporter csvExporter = new CSVExporter(pathExtended, project.getName());
                            csvExporter.exportResultToCSV(project.getName(), experimentSplitter.getName(), classModel, realistic, result);
                            System.out.println("Info | framevpm.Main.main() | Finished writing approach results from " + approach.getApproachName());
                        }
                    } catch (Exception ex) {
                        System.out.println("Error | framevpm.Main.main() | " + approach.getApproachName() + " | " + ex.getStackTrace().toString());
                    }
                }
                //*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
