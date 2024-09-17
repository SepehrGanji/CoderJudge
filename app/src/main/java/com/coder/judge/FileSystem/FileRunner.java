package com.coder.judge.FileSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

public class FileRunner {

    private static FileRunner _instance = null;
    private static final Logger log = Logger.getLogger(FileRunner.class.getName());

    private FileRunner() {
    }

    public static FileRunner getInstance() {
        if (_instance == null) {
            _instance = new FileRunner();
        }
        return _instance;
    }

    public int runFile(String fileName, List<String> args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python3", "other/" + fileName);
            pb.command().addAll(args);
            pb.redirectErrorStream(true);

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug(line);
            }
            return process.waitFor();
        } catch (Exception e) {
            log.error("Error in CompileService: " + e.getMessage());
        }
        return -1;
    }
}