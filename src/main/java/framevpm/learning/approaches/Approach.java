package framevpm.learning.approaches;

import framevpm.learning.Classifiers;
import framevpm.learning.model.ApproachResult;
import framevpm.learning.model.Experiment;
import framevpm.learning.model.ExperimentResult;
import framevpm.learning.model.classmodel.ClassModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Approach {

    protected final List<Experiment> experiments;
    public final ClassModel model;
    protected LinkedHashMap<String, Instances[]> preparedInstances;

    public Approach(List<Experiment> experiments, ClassModel model) {
        this.experiments = experiments;
        this.model = model;
        preparedInstances = new LinkedHashMap<>();
    }

    public abstract void prepareInstances();

    public ApproachResult runWith(String classifierName, boolean smote) {
        Map<String, Map<String, ExperimentResult>> resultMap = new LinkedHashMap<>();
        final int[] i = {0};
        preparedInstances.forEach((experiment, instances) -> {
            try {
                Classifier classifier = Classifiers.getClassifier(classifierName);
                Instances training;
                if (smote) {
                    Filter filter = new SMOTE();
                    filter.setInputFormat(instances[0]);
                    training = Filter.useFilter(instances[0], filter);
                } else {
                    training = instances[0];
                }

                classifier.buildClassifier(training);
                Map<String, ExperimentResult> experimentResultMap = new LinkedHashMap<>();
                Experiment exp = experiments.get(i[0]);
                Experiment experimentdata = exp.loadExperiment(exp.getFullFileName());
                final int[] j = {0};
                experimentdata.getTesting().keySet().forEach(fileMetaInf -> {
                    if (model.correspondingToTypeFile(fileMetaInf.getType()) != null) {
                        try {
                            Instance testingInstance = instances[1].instance(j[0]);
                            double[] dist = classifier.distributionForInstance(testingInstance);
                            double classif = classifier.classifyInstance(testingInstance);
                            experimentResultMap.put(fileMetaInf.getFile(), new ExperimentResult(dist, classif, fileMetaInf, testingInstance.classValue()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            j[0]++;
                        }
                    }
                });
                resultMap.put(experiment, experimentResultMap);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                i[0]++;
            }
        });
        return new ApproachResult(getApproachName(), smote, classifierName, resultMap);
    }

    public abstract String getApproachName();
    
}
