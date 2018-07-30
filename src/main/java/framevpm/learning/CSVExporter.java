package framevpm.learning;

import com.opencsv.CSVWriter;
import framevpm.ResourcesPathExtended;
import framevpm.learning.model.ApproachResult;
import framevpm.learning.model.classmodel.ClassModel;
import framevpm.learning.splitter.fileMeta.VulnerabilityInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static data7.Utils.checkFolderDestination;

public class CSVExporter {

    private final String rpath;

    public CSVExporter(ResourcesPathExtended resourcesPathExtended) {
        this.rpath = resourcesPathExtended.getStatPath();
        checkFolderDestination(rpath);
    }

    public void exportResultToCSV(String project, String split, ClassModel model, ApproachResult approachResult) throws IOException {
        String path = rpath;
        checkFolderDestination(path);
        path += project + "/";
        checkFolderDestination(path);
        path += split + "/";
        checkFolderDestination(path);
        path += model.getName() + "/";
        checkFolderDestination(path);
        path += approachResult.getApproach();
        checkFolderDestination(path);
        CSVWriter writerom = new CSVWriter(new FileWriter(new File(path + approachResult.getClassifier() + "-" + approachResult.isSmote() + ".csv"), false));

        List<String> listClass = model.getClassList();
        int size = 7 + listClass.size();
        String[] header = new String[size];
        header[0] = "Experiment";
        header[1] = "File";
        header[2] = "Class";
        header[3] = "CWE";
        header[4] = "CVSS";
        header[5] = "Expected";
        header[6] = "Prediction";
        for (int i = 0; i < listClass.size(); i++) {
            header[7 + i] = "Probability of" + listClass.get(i);
        }

        writerom.writeNext(header);

        approachResult.getResultMap().forEach((experiment, experimentResultMap) ->
                experimentResultMap.forEach((file, experimentresult) -> {
                    String[] line = new String[size];
                    line[0] = experiment;
                    line[1] = file;
                    line[2] = experimentresult.getFileMetaInf().getType().name();
                    VulnerabilityInfo vulnerabilityInfo = experimentresult.getFileMetaInf().getVulnerabilityInfo();
                    if (vulnerabilityInfo != null) {
                        line[3] = vulnerabilityInfo.getCwe();
                        line[4] = String.valueOf(vulnerabilityInfo.getCvss());
                    }
                    line[5] = listClass.get((int) experimentresult.getExpectedClassif());
                    line[6] = listClass.get((int) experimentresult.getClassification());
                    for (int i = 0; i < listClass.size(); i++) {
                        line[7 + i] = String.valueOf(experimentresult.getDistribresult()[i]);
                    }
                    writerom.writeNext(line);
                }));
        writerom.close();
    }
}
