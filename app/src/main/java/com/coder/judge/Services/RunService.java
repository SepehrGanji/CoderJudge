package com.coder.judge.Services;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

import com.coder.judge.DB.SubmissionEntity;
import com.coder.judge.FileSystem.FileRunner;

public class RunService implements Runnable {

    private static RunService _instance = null;
    private static final Logger log = Logger.getLogger(CompileService.class.getName());

    private RunService() {
    }

    public static RunService getInstance() {
        if (_instance == null) {
            _instance = new RunService();
        }
        return _instance;
    }

    @Override
    public void run() {
        String submissionId = SubmissionEntity.getInstance().getOneCompiledSubmission();
        if (submissionId.equals("")) {
            log.debug("Nothing to run...");
            return;
        }
        submissionId = "1";
        log.info("Running submission: " + submissionId);
        String lang = SubmissionEntity.getInstance().getLang(submissionId);
        String codePath = System.getenv("FILE_PATH") + "/S/" + submissionId;
        int question = SubmissionEntity.getInstance().getQuestion(submissionId);
        String questionPath = System.getenv("FILE_PATH") + "/Q/" + question;
        List<String> args = List.of(lang, codePath, questionPath);
        int stat = FileRunner.getInstance().runFile("run.py", args);
        log.info("Run status: " + stat);
        try {
            String outputFileName = "other/run.py.out";
            BufferedReader reader = Files.newBufferedReader(Paths.get(outputFileName));
            String line;
            String mamad = "ACCEPT";
            while ((line = reader.readLine()) != null) {
                if(!"ACCEPT".equals(line)) {
                    mamad = line;
                    break;
                }
            }
            log.info("Run result: " + mamad);
        } catch (Exception e) {
            log.error("Error in RunService: " + e.getMessage());
        }
    }
}
