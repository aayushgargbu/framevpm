package framevpm.analyze.approaches.naturalness;

import data7.Resources;
import framevpm.ResourcesPathExtended;
import framevpm.analyze.Analyze;
import framevpm.analyze.approaches.naturalness.setup.NaturalnessSetup;
import framevpm.analyze.model.FileAnalysis;
import framevpm.analyze.model.ProjectAnalysis;
import framevpm.analyze.model.ProjectReleaseAnalysed;
import framevpm.analyze.model.ReleaseAnalysis;
import framevpm.organize.model.FileData;
import framevpm.organize.model.ReleaseData;
import modelling.NgramModel;
import modelling.infrastructure.NgramModelKylmImpl;
import tokenizer.file.java.exception.UnparsableException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class OtherFilesNaturalness extends Analyze {
    public final static String NAME = "Other Files Naturalness";
    private final NaturalnessSetup setup;


    public OtherFilesNaturalness(ResourcesPathExtended pathExtended, String project, NaturalnessSetup setup) throws IOException, ClassNotFoundException {
        super(pathExtended, project);
        this.setup = setup;
    }

    @Override
    public ProjectReleaseAnalysed processFeatures() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(2);//(Resources.NB_THREADS);
        CompletionService<FileAnalysis> completionService = new ExecutorCompletionService(executor);
        System.out.println("Starting: " + getApproachName());
        try {
            for (String release : releases) {
                ReleaseData releaseData = projectData.getOrCreateRelease(release);
                if (releaseData.getFileMap().size() != 0) {
                    Map<String, Iterable<String>> tokenizedFiles = new HashMap<>();
                    Map<String, String> files = loadVersion(release);
                    for (Map.Entry<String, String> fileEntry : files.entrySet()) {
                        tokenizedFiles.put(fileEntry.getKey(), setup.getTokenizer().tokenize(fileEntry.getValue()));
                    }
                    System.out.println("Starting: " + release);
                    ReleaseAnalysis releaseAnalysis = exporter.loadReleaseAnalysis(project, release);
                    if (releaseAnalysis == null) {
                        releaseAnalysis = new ReleaseAnalysis(release);//projectAnalysis.getOrCreateReleaseAnalysis(release);
                    }
                    if (releaseAnalysis.addApproache(getApproachName())) {
                        int count = 0;
                        for (Map.Entry<String, Iterable<String>> fileEntry : tokenizedFiles.entrySet()) {
                            FileAnalysis fa = releaseAnalysis.getOrCreateFileAnalysis(fileEntry.getKey());
                            completionService.submit(handleFile(releaseData, fileEntry, tokenizedFiles, fa));
                            count++;
                        }
                        int received = 0;
                        int error = 0;
                        while (received < count) {
                            Future<FileAnalysis> fut = completionService.take();
                            try {
                                FileAnalysis result = fut.get(60, TimeUnit.SECONDS);
                                if (result != null) {
                                    releaseAnalysis.getFileAnalysisMap().put(result.getFile(), result);
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                error++;
                            } catch (TimeoutException e) {
                                fut.cancel(true);
                                error++;
                            } finally {
                                received++;
                                System.out.println(received + "/" + count);
                            }
                        }
                        System.out.println("error: " + error);
                        System.out.println("done rel: " + release);
                        exporter.saveReleaseAnalysis(releaseAnalysis, project);
                        projectAnalysis.getReleaseAnalyzed().add(release);
                    }
                }
            }
        } catch (InterruptedException | UnparsableException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            exporter.saveProjectReleaseAnalysis(projectAnalysis);
            return projectAnalysis;
        }
    }

    private Callable<FileAnalysis> handleFile(ReleaseData releaseData, Map.Entry<String, Iterable<String>> fileEntry, Map<String, Iterable<String>> tokenizedFiles, FileAnalysis fa) {
        return () -> {
            List<Iterable<String>> training = tokenizedFiles.entrySet().stream().filter(stringIterableEntry -> !stringIterableEntry.getKey().equals(fileEntry.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
            NgramModel model = new NgramModelKylmImpl(setup.getN(), setup.getSmoother(), setup.getThreshold());
            model.train(training);
            FileData data = releaseData.getFile(fileEntry.getKey());
            return NaturalnessComputation.computeNaturalness(data, model, fileEntry.getValue(), setup.getTokenizer(), getApproachName(), fa);
        };
    }

    @Override
    public String getApproachName() {
        return NAME + setup;
    }
}
