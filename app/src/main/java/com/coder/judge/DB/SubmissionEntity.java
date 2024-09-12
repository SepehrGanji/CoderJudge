package com.coder.judge.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.coder.judge.Model.Submission;

public class SubmissionEntity {

    private static SubmissionEntity _instance = null;

    private SubmissionEntity() {
    }

    public static SubmissionEntity getInstance() {
        if (_instance == null) {
            _instance = new SubmissionEntity();
        }
        return _instance;
    }

    public void insert(Submission submission) {
        try {
            Connection conn = DB.connection();
            String query = "INSERT INTO submissions (id, lang, code) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, submission.getId());
            stmt.setString(2, submission.getLang());
            stmt.setString(3, submission.getCode());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
