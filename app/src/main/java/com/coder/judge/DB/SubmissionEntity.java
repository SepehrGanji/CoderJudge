package com.coder.judge.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.coder.judge.Model.Submission;

public class SubmissionEntity {

    private static SubmissionEntity _instance = null;

    private SubmissionEntity() {
    }

    private String getOneSubmission(String status) {
        try {
            Connection conn = DB.connection();
            String query = "SELECT id FROM submission WHERE status = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
            String query = "INSERT INTO submission (id, question, status, lang) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, submission.getId());
            stmt.setInt(2, submission.getQuestion());
            stmt.setString(3, "PENDING"); //TODO: Enum for status
            stmt.setString(4, submission.getLang());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOnePendingSubmission() {
        return getOneSubmission("PENDING");
    }

    public String getOneCompiledSubmission() {
        return getOneSubmission("COMPILED");
    }

    public String getLang(String submissionId) {
        try {
            Connection conn = DB.connection();
            String query = "SELECT lang FROM submission WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, submissionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getQuestion(String submissionId) {
        try {
            Connection conn = DB.connection();
            String query = "SELECT question FROM submission WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, submissionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateStatus(String submissionId, String status) {
        try {
            Connection conn = DB.connection();
            String query = "UPDATE submission SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setString(2, submissionId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
