package framevpm.learning.approaches.ifc;

import framevpm.analyze.approaches.filebyfile.FileFunctionCalls;
import framevpm.analyze.model.Analysis;
import framevpm.learning.approaches.Approach;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.FileMetaInf;
import framevpm.learning.model.classmodel.ClassModel;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

import java.util.*;

@SuppressWarnings("Duplicates")
public class FunctionCallsApproach extends Approach {

    public FunctionCallsApproach(List<Experiment> experiments, ClassModel model) {
        super(experiments, model);
    }

    @Override
    public void prepareInstances() {
        for (Experiment ex : experiments) {
            try {
                Experiment experiment = ex.loadExperiment(ex.getFullFileName());
                ArrayList<Attribute> featureVector = generateFeatureVector(experiment.getTraining());
                if (featureVector.size() > 1) {
                    Instances training = generateInstances("training", experiment.getTraining(), featureVector);
                    Instances testing = generateInstances("testing", experiment.getTesting(), featureVector);
                    preparedInstances.put(experiment.getName(), new Instances[]{training, testing});
                }
            } catch (Exception exc) {
                System.out.println("Error for " + ex.getFullFileName()
                        + " | framevpm.learning.approaches.ifc.FunctionCallsApproach.prepareInstances() |");
                exc.printStackTrace();
            }
        }
    }

    private Instances generateInstances(String type, LinkedHashMap<FileMetaInf, Map<String, Analysis>> set, ArrayList<Attribute> featureVector) {
        Instances instances = new Instances(type, featureVector, set.size());
        instances.setClassIndex(featureVector.size() - 1);
        set.forEach((fileMetaInf, stringAnalysisMap) -> {
            try{
            Instance instance = generateInstance(featureVector, fileMetaInf, stringAnalysisMap);
            if (instance != null) {
                instance.setDataset(instances);
                instances.add(instance);
            }
            } catch (Exception ex){
                System.out.println("Error | framevpm.learning.approaches.ifc.FunctionCallsApproach.generateInstances() | Skiping");
                ex.printStackTrace();
            }
        });
        return instances;
    }

    private Instance generateInstance(ArrayList<Attribute> featureVector, FileMetaInf fileMetaInf, Map<String, Analysis> stringAnalysisMap) {
        double[] values = new double[featureVector.size()];
        String type = model.correspondingToTypeFile(fileMetaInf.getType());
        if (type == null) {
            return null;
        }
        for (int i = 0; i < featureVector.size() - 1; i++) {
            String name = featureVector.get(i).name();
            values[i] = (int) stringAnalysisMap.get(FileFunctionCalls.NAME).getFeatureMap().getOrDefault(name, 0);
        }
        values[featureVector.size() - 1] = model.getClassList().indexOf(type);
        return new SparseInstance(1, values);
    }

    private ArrayList<Attribute> generateFeatureVector(LinkedHashMap<FileMetaInf, Map<String, Analysis>> experiment) {
        Map<String, Map<String, Integer>> countFC = new HashMap<>();
        Map<String, Integer> countTotal = new HashMap<>();
        for (String mod : model.getClassList()) {
            countFC.put(mod, new HashMap<>());
            countTotal.put(mod, 0);
        }
        experiment.forEach((file, analysis) -> {
            try {
                String type = model.correspondingToTypeFile(file.getType());
                if (type != null) {
                    countTotal.put(type, countTotal.get(type) + 1);
                    Map<String, Integer> FCfoType = countFC.get(type);
                    analysis.get(FileFunctionCalls.NAME).getFeatureMap().keySet().forEach(fc
                            -> FCfoType.put(fc, FCfoType.getOrDefault(fc, 0) + 1));
                }
            } catch (Exception ex) {
                System.out.println("Error | framevpm.learning.approaches.ifc.FunctionCallsApproach.generateFeatureVector() | Skipping");
                ex.printStackTrace();
            }
        });
        Set<String> functionCalls = new HashSet<>();
        for (String mod : model.getClassList()) {
            int threshold = (countTotal.get(mod) * 5 / 100) + 1;
            Map<String, Integer> FCfoType = countFC.get(mod);
            FCfoType.entrySet().stream().filter(stringIntegerEntry -> stringIntegerEntry.getValue() > threshold).forEach(stringIntegerEntry -> functionCalls.add(stringIntegerEntry.getKey()));
        }

        ArrayList<Attribute> attributes = new ArrayList<>();
        functionCalls.forEach(stringEntry -> attributes.add(new Attribute(stringEntry)));

        attributes.add(new Attribute("theClass", model.getClassList()));
        return attributes;
    }

    @Override
    public String getApproachName() {
        return "FunctionCalls";
    }
}
