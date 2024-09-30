package com.coder.judge.Services;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

import com.coder.judge.DB.QuestionEntity;
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
        log.info("Running submission: " + submissionId);
        String lang = SubmissionEntity.getInstance().getLang(submissionId);
        String codePath = System.getenv("FILE_PATH") + "/S/" + submissionId;
        int question = SubmissionEntity.getInstance().getQuestion(submissionId);
        String questionPath = System.getenv("FILE_PATH") + "/Q/" + question;
        int timeLimit = QuestionEntity.getInstance().getLimit(question, "timelimit");
        int memoryLimit = QuestionEntity.getInstance().getLimit(question, "memlimit");
        List<String> args = List.of(lang, codePath, questionPath, String.valueOf(timeLimit), String.valueOf(memoryLimit));
        FileRunner.getInstance().runFile("run.py", args);
        try {
            String outputFileName = "other/run.py.out";
            BufferedReader reader = Files.newBufferedReader(Paths.get(outputFileName));
            String line;
            String run_status = "ACCEPT";
            while ((line = reader.readLine()) != null) {
                if(!"ACCEPT".equals(line)) {
                    run_status = line;
                    break;
                }
            }
            log.info("Run status: " + run_status);
            SubmissionEntity.getInstance().updateStatus(submissionId, run_status);
        } catch (Exception e) {
            log.error("Error in RunService: " + e.getMessage());
        }
    }
}
