package framevpm.learning.model;

import framevpm.analyze.model.Analysis;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Experiment implements Serializable {

    private static final long serialVersionUID = 20180727L;

    private final String name;
    private final LinkedHashMap<FileMetaInf, Map<String, Analysis>> training;

    private final LinkedHashMap<FileMetaInf, Map<String, Analysis>> testing;

    private String fileFullName;

    public Experiment(String name, LinkedHashMap<FileMetaInf, Map<String, Analysis>> training, LinkedHashMap<FileMetaInf, Map<String, Analysis>> testing) {
        this.name = name;
        this.training = training;
        this.testing = testing;
    }

    public Experiment(String name, String fullFileName) {
        this.name = name;
        this.training = null;
        this.testing = null;
        this.fileFullName = fullFileName;
    }

    public LinkedHashMap<FileMetaInf, Map<String, Analysis>> getTraining() {
        return training;
    }

    public LinkedHashMap<FileMetaInf, Map<String, Analysis>> getTesting() {
        return testing;
    }

    public String getName() {
        return name;
    }

    public String getFullFileName() {
        return fileFullName;
    }

    public Experiment loadExperiment(String fullFileName) {
        try {
            File file = new File(fullFileName);
            if (file.exists()) {
                System.out.println("Info | framevpm.learning.model.Experiment.loadExperiment() | Found saved experiment at " + fullFileName);
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream read = new ObjectInputStream(fileIn);
                Experiment data = (Experiment) read.readObject();
                read.close();
                fileIn.close();
                return data;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Error | framevpm.learning.model.Experiment.loadExperiment() | " + ex.getStackTrace().toString());
            return null;
        }
    }
}
