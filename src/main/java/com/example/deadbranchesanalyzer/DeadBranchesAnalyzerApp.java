package com.example.deadbranchesanalyzer;

import com.example.deadbranchesanalyzer.model.BranchInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeadBranchesAnalyzerApp {
    public static void main(String[] args) {
        String token = System.getenv("GITHUB_TOKEN");
        if (token == null || token.isEmpty()) {
            System.out.println("GitHub token is missing!");
            return;
        }
        // Please put here your username and branch name to analyze
        String repo = "your_username/your_branch_name";

        GitHubApiClient client = new GitHubApiClient(token);

        List<BranchInfo> branches = client.getBranches(repo);

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

        long currentTimeMillis = System.currentTimeMillis();

        long sixMonthsMillis = 6L * 30 * 24 * 60 * 60 * 1000;

        for (BranchInfo branch : branches) {
            String lastCommitDate = branch.getLastCommitDate();
            String formattedDate = lastCommitDate;

            if (!lastCommitDate.equals("Unknown")) {
                try {
                    Date date = originalFormat.parse(lastCommitDate);
                    formattedDate = targetFormat.format(date);

                    long lastCommitTimeMillis = date.getTime();
                    if (currentTimeMillis - lastCommitTimeMillis > sixMonthsMillis) {
                        System.out.printf("🌿 %s | 📅 Last commit: %s (Dead branch!)%n", branch.getName(), formattedDate);
                    } else {
                        System.out.printf("🌿 %s | 📅 Last commit: %s%n", branch.getName(), formattedDate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
