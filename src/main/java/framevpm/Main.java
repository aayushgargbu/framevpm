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

public class Main {

    public static void main(String[] args) {
        ResourcesPathExtended pathExtended = new ResourcesPathExtended("C:/GitHub/framevpm/repository/"); //("/home/matthieu/vpm/");
        ExporterExtended exporterExtended = new ExporterExtended(pathExtended);
        CSVExporter csvExporter = new CSVExporter(pathExtended);
        Project[] projects = new Project[]{//CProjects.SYSTEMD, CProjects.OPEN_SSL,
            //CProjects.WIRESHARK, 
            CProjects.LINUX_KERNEL};
        for (Project project : projects) {
            try {
                //Training
                ///*
                new Importer(pathExtended).updateOrCreateDatasetFor(project);
                System.gc();
                //new BugCollector(pathExtended).updateOrCreateBugDataset(project.getName());
                //System.gc();
                new Organize(pathExtended, project.getName()).balance(false);//(true);
                System.gc();
                new Application(pathExtended, project.getName()).runAll();
                System.gc();
                //*/
                //Testing
                ///*
                ClassModel classModel = new VulNotVul();
                ReleaseSplitter experimentSplitter = new GeneralSplit(pathExtended, project.getName());
                //ReleaseSplitter experimentSplitter = new ThreeLastSplit(pathExtended, project.getName());
                List<Experiment> experimentList = experimentSplitter.generateExperiment();
                System.out.println("Info | framevpm.Main.main() | Finished generating experiments");
                Approach[] approaches = {
                    //new NaturalnessAndCM(experimentList, model),
                    //new PureNaturalness(experimentList, model),
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

                        ApproachResult result = approach.runWith("RandomForest", true);
                        System.out.println("Info | framevpm.Main.main() | Finished getting approach results from " + approach.getApproachName());

                        exporterExtended.saveApproachResult(project.getName(), experimentSplitter.getName(), classModel.getName(), true, result);
                        System.out.println("Info | framevpm.Main.main() | Finished saving approach results from " + approach.getApproachName());

                        csvExporter.exportResultToCSV(project.getName(), experimentSplitter.getName(), classModel, true, result);
                        System.out.println("Info | framevpm.Main.main() | Finished writing approach results from " + approach.getApproachName());
                    } catch (Exception ex) {
                        System.out.println("Error | framevpm.Main.main() | " + approach.getApproachName() + " | " + ex.getStackTrace().toString());
                    }
                }
                //*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
