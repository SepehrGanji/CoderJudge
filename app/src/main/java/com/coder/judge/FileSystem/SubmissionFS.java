package com.coder.judge.FileSystem;

import com.coder.judge.Model.Submission;

public class SubmissionFS {

    private static SubmissionFS _instance = null;

    private SubmissionFS() {
    }

    public static SubmissionFS getInstance() {
        if (_instance == null) {
            _instance = new SubmissionFS();
        }
        return _instance;
    }

    public void save(Submission submission) {
        // Save submission to file system
    }
}
