package com.coder.judge.Services;

import java.util.List;

import org.apache.log4j.Logger;

import com.coder.judge.DB.SubmissionEntity;
import com.coder.judge.FileSystem.FileRunner;

public class CompileService implements Runnable {

    private static CompileService _instance = null;
    private static final Logger log = Logger.getLogger(CompileService.class.getName());

    private CompileService() {
    }

    public static CompileService getInstance() {
        if (_instance == null) {
            _instance = new CompileService();
        }
        return _instance;
    }

    @Override
    public void run() {
        String submissionId = SubmissionEntity.getInstance().getOnePendingSubmission();
        if (submissionId.equals("")) {
            log.debug("Nothing to compile...");
            return;
        }
        log.info("Compiling submission: " + submissionId);
        String lang = SubmissionEntity.getInstance().getLang(submissionId);
        String fullPath = System.getenv("FILE_PATH") + "/S/" + submissionId;
        List<String> args = List.of(lang, fullPath);
        int stat = FileRunner.getInstance().runFile("compile.py", args);
        log.info("Compilation status: " + stat);
        String status = stat == 0 ? "COMPILED" : "COMPILE-ERROR";
        SubmissionEntity.getInstance().updateStatus(submissionId, status);
    }
}
