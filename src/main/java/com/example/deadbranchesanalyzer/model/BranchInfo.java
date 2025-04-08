package com.example.deadbranchesanalyzer.model;

public class BranchInfo {
    private final String name;
    private final String lastCommitDate;

    public BranchInfo(String name, String lastCommitDate) {
        this.name = name;
        this.lastCommitDate = lastCommitDate;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitDate() {
        return lastCommitDate;
    }
}
