package com.coder.judge.FileSystem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import com.coder.judge.Model.Submission;

public class SubmissionFS {

    private static SubmissionFS _instance = null;
    private String rootPath = System.getenv("FILE_PATH") + "/S";

    private SubmissionFS() {
    }

    public static SubmissionFS getInstance() {
        if (_instance == null) {
            _instance = new SubmissionFS();
        }
        return _instance;
    }

    public void save(Submission submission) {
        Path folderPath = Path.of(rootPath, String.valueOf(submission.getId()));
        Path filePath = Path.of(folderPath.toString(), "code.txt");
        try {
            Files.createDirectories(folderPath);
            byte[] decodedBytes = Base64.getDecoder().decode(submission.getCode());
            String decodedCode = new String(decodedBytes);
            Files.writeString(filePath, decodedCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
