package framevpm.learning.approaches.ifc;

import framevpm.analyze.approaches.filebyfile.FileFunctionCalls;
import framevpm.analyze.approaches.filebyfile.FileIncludes;
import framevpm.analyze.model.Analysis;
import framevpm.learning.approaches.Approach;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.FileMetaInf;
import framevpm.learning.model.classmodel.ClassModel;
import weka.core.*;

import java.util.*;

public class IncludesApproach extends Approach {

    public IncludesApproach(List<Experiment> experiments, ClassModel model) {
        super(experiments, model);
    }

    @Override
    public void prepareInstances() {
        for (Experiment ex : experiments) {
            Experiment experiment = ex.loadExperiment(ex.getFullFileName());
            ArrayList<Attribute> featureVector = generateFeatureVector(experiment.getTraining());
            if (featureVector.size() > 1) {
                Instances training = generateInstances("training", experiment.getTraining(), featureVector);
                Instances testing = generateInstances("testing", experiment.getTesting(), featureVector);
                preparedInstances.put(experiment.getName(), new Instances[]{training, testing});
            }
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
        String type = model.correspondingToTypeFile(fileMetaInf.getType());
        if (type == null) return null;
        for (int i = 0; i < featureVector.size() - 1; i++) {
            String name = featureVector.get(i).name();
            if (stringAnalysisMap.get(FileIncludes.NAME).getFeatureMap().containsKey(name)) values[i] = 1;
            else values[i] = 0;
        }
        values[featureVector.size() - 1] = model.getClassList().indexOf(type);
        return new SparseInstance(1, values);
    }

    private ArrayList<Attribute> generateFeatureVector(LinkedHashMap<FileMetaInf, Map<String, Analysis>> experiment) {
        Map<String, Map<String, Integer>> countInclude = new HashMap<>();
        Map<String, Integer> countTotal = new HashMap<>();
        for (String mod : model.getClassList()) {
            countInclude.put(mod, new HashMap<>());
            countTotal.put(mod, 0);
        }
        experiment.forEach((file, analysis) -> {
            String type = model.correspondingToTypeFile(file.getType());
            if (type != null) {
                countTotal.put(type, countTotal.get(type) + 1);
                Map<String, Integer> includetoType = countInclude.get(type);
                analysis.get(FileIncludes.NAME).getFeatureMap().keySet().forEach(fc ->
                        includetoType.put(fc, includetoType.getOrDefault(fc, 0) + 1));
            }
        });
        Set<String> includes = new HashSet<>();
        for (String mod : model.getClassList()) {
            int threshold = (countTotal.get(mod) * 3 / 100) + 1;
            Map<String, Integer> FCfoType = countInclude.get(mod);
            FCfoType.entrySet().stream().filter(stringIntegerEntry -> stringIntegerEntry.getValue() > threshold).forEach(stringIntegerEntry -> includes.add(stringIntegerEntry.getKey()));
        }

        ArrayList<Attribute> attributes = new ArrayList<>();
        includes.forEach(stringEntry -> attributes.add(new Attribute(stringEntry)));

        attributes.add(new Attribute("theClass", model.getClassList()));
        return attributes;
    }

    @Override
    public String getApproachName() {
        return "Includes";
    }
}
