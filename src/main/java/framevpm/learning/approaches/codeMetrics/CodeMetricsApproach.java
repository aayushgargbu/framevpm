package framevpm.learning.approaches.codeMetrics;

import framevpm.analyze.approaches.filebyfile.CodeChurnDeveloperMetrics;
import framevpm.analyze.approaches.filebyfile.ComplexityMetrics;
import framevpm.analyze.approaches.filebyfile.SimpleCodeMetrics;
import framevpm.analyze.model.Analysis;
import framevpm.learning.approaches.Approach;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.FileMetaInf;
import framevpm.learning.model.classmodel.ClassModel;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class CodeMetricsApproach extends Approach {

    public CodeMetricsApproach(List<Experiment> experiments, ClassModel model) {
        super(experiments, model);
    }

    public void prepareInstances() {
        ArrayList<Attribute> featureVector = generateFVCodeMetricsALL(model.getClassList());
        for (Experiment ex : experiments) {
            Experiment experiment = ex.loadExperiment(ex.getFullFileName());
            Instances training = generateInstances("training", experiment.getTraining(), featureVector);
            Instances testing = generateInstances("testing", experiment.getTesting(), featureVector);
            preparedInstances.put(experiment.getName(), new Instances[]{training, testing});
        }
    }

    private Instances generateInstances(String type, LinkedHashMap<FileMetaInf, Map<String, Analysis>> set, ArrayList<Attribute> featureVector) {
        Instances instances = new Instances(type, featureVector, set.size());
        instances.setClassIndex(featureVector.size() - 1);
        set.forEach((fileMetaInf, stringAnalysisMap) -> {
            Instance instance = generateInstance(featureVector, fileMetaInf, stringAnalysisMap);
            if (instance != null) {
                instance.setDataset(instances);
                instances.add(instance);
            }
        });
        return instances;
    }

    private Instance generateInstance(ArrayList<Attribute> featureVector, FileMetaInf fileMetaInf, Map<String, Analysis> stringAnalysisMap) {
        double[] values = new double[featureVector.size()];
        int i = 0;
        String type = model.correspondingToTypeFile(fileMetaInf.getType());
        if (type == null) {
            return null;
        }
        for (Attribute attribute : featureVector) {
            try {
                switch (attribute.name()) {
                    case "loc":
                        values[i] = (int) stringAnalysisMap.get(SimpleCodeMetrics.NAME).getFeatureMap().get("loc");
                        break;
                    case "preprocessorLines":
                        values[i] = (int) stringAnalysisMap.get(SimpleCodeMetrics.NAME).getFeatureMap().get("preprocessorLines");
                        break;
                    case "commentDensity":
                        values[i] = (float) stringAnalysisMap.get(SimpleCodeMetrics.NAME).getFeatureMap().get("commentDensity");
                        break;
                    case "countDeclFunction":
                        values[i] = (int) stringAnalysisMap.get(SimpleCodeMetrics.NAME).getFeatureMap().get("countDeclFunction");
                        break;
                    case "countDeclVariable":
                        values[i] = (int) stringAnalysisMap.get(SimpleCodeMetrics.NAME).getFeatureMap().get("countDeclVariable");
                        break;

                    case "ccAverage":
                        values[i] = (double) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("ccAverage");
                        break;
                    case "ccMax":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("ccMax");
                        break;
                    case "ccSum":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("ccSum");
                        break;
                    case "sccMax":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("sccMax");
                        break;
                    case "sccAverage":
                        values[i] = (double) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("sccAverage");
                        break;
                    case "sccSum":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("sccSum");
                        break;
                    case "mccMax":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("mccMax");
                        break;
                    case "mccAverage":
                        values[i] = (double) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("mccAverage");
                        break;
                    case "mccSum":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("mccSum");
                        break;
                    case "ecMax":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("ecMax");
                        break;
                    case "ecAverage":
                        values[i] = (double) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("ecAverage");
                        break;
                    case "ecSum":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("ecSum");
                        break;

                    case "maxNestingMax":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("maxNestingMax");
                        break;
                    case "maxNestingAverage":
                        values[i] = (double) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("maxNestingAverage");
                        break;
                    case "maxNestingSum":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("maxNestingSum");
                        break;

                    case "fanInMax":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("fanInMax");
                        break;
                    case "fanInAverage":
                        values[i] = (double) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("fanInAverage");
                        break;
                    case "fanInSum":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("fanInSum");
                        break;
                    case "fanOutMax":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("fanOutMax");
                        break;
                    case "fanOutAverage":
                        values[i] = (double) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("fanOutAverage");
                        break;
                    case "fanOutSum":
                        values[i] = (int) stringAnalysisMap.get(ComplexityMetrics.NAME).getFeatureMap().get("fanOutSum");
                        break;

                    case "linesAdded":
                        values[i] = (int) stringAnalysisMap.get(CodeChurnDeveloperMetrics.NAME).getFeatureMap().get("linesAdded");
                        break;
                    case "linesModified":
                        values[i] = (int) stringAnalysisMap.get(CodeChurnDeveloperMetrics.NAME).getFeatureMap().get("linesModified");
                        break;
                    case "linesDeleted":
                        values[i] = (int) stringAnalysisMap.get(CodeChurnDeveloperMetrics.NAME).getFeatureMap().get("linesDeleted");
                        break;
                    case "numberOfChanges":
                        values[i] = (int) stringAnalysisMap.get(CodeChurnDeveloperMetrics.NAME).getFeatureMap().get("numberOfChanges");
                        break;
                    case "totalNumberOfDeveloper":
                        values[i] = (int) stringAnalysisMap.get(CodeChurnDeveloperMetrics.NAME).getFeatureMap().get("totalNumberOfDeveloper");
                        break;
                    case "currentNumberOfDeveloper":
                        values[i] = (int) stringAnalysisMap.get(CodeChurnDeveloperMetrics.NAME).getFeatureMap().get("currentNumberOfDeveloper");
                        break;
                    case "theClass":
                        values[i] = model.getClassList().indexOf(type);
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Error in Switch Case for " + attribute.name() 
                        + " | framevpm.learning.approaches.codeMetrics.CodeMetricsApproach.generateInstance() | " 
                        + ex.getStackTrace().toString());
                values[i] = -1;
            }
            i++;
        }

        for (int k = 0; k < values.length; k++) {
            if (Double.isNaN(values[k])) {
                values[k] = 0;
            }
        }
        return new DenseInstance(1, values);
    }

    private static ArrayList<Attribute> generateFVCodeMetricsALL(List<String> classValues) {

        ArrayList<Attribute> featureVector = new ArrayList<>();
        //Complexity
        featureVector.add(new Attribute("loc"));
        featureVector.add(new Attribute("preprocessorLines"));
        featureVector.add(new Attribute("commentDensity"));
        featureVector.add(new Attribute("countDeclFunction"));
        featureVector.add(new Attribute("countDeclVariable"));
        featureVector.add(new Attribute("ccAverage"));
        featureVector.add(new Attribute("ccMax"));
        featureVector.add(new Attribute("ccSum"));
        featureVector.add(new Attribute("sccMax"));
        featureVector.add(new Attribute("sccAverage"));
        featureVector.add(new Attribute("sccSum"));
        featureVector.add(new Attribute("mccMax"));
        featureVector.add(new Attribute("mccAverage"));
        featureVector.add(new Attribute("mccSum"));
        featureVector.add(new Attribute("ecMax"));
        featureVector.add(new Attribute("ecAverage"));
        featureVector.add(new Attribute("ecSum"));
        featureVector.add(new Attribute("maxNestingMax"));
        featureVector.add(new Attribute("maxNestingAverage"));
        featureVector.add(new Attribute("maxNestingSum"));
        featureVector.add(new Attribute("fanInMax"));
        featureVector.add(new Attribute("fanInAverage"));
        featureVector.add(new Attribute("fanInSum"));
        featureVector.add(new Attribute("fanOutMax"));
        featureVector.add(new Attribute("fanOutAverage"));
        featureVector.add(new Attribute("fanOutSum"));

        featureVector.add(new Attribute("linesAdded"));
        featureVector.add(new Attribute("linesModified"));
        featureVector.add(new Attribute("linesDeleted"));

        featureVector.add(new Attribute("numberOfChanges"));
        featureVector.add(new Attribute("totalNumberOfDeveloper"));
        featureVector.add(new Attribute("currentNumberOfDeveloper"));

        featureVector.add(new Attribute("theClass", classValues));

        return featureVector;
    }

    @Override
    public String getApproachName() {
        return "CodeMetrics";
    }
}
