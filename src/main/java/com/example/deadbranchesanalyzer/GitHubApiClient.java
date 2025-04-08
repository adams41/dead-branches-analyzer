package com.example.deadbranchesanalyzer;

import com.example.deadbranchesanalyzer.model.BranchInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitHubApiClient {
    private final OkHttpClient client = new OkHttpClient();
    private final String token;
    private final ObjectMapper mapper = new ObjectMapper();

    public GitHubApiClient(String token) {
        this.token = token;
    }

    public List<BranchInfo> getBranches(String repo) {
        List<BranchInfo> branches = new ArrayList<>();

        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + repo + "/branches")
                .header("Authorization", "token " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {

            JsonNode root = mapper.readTree(response.body().string());

            for (JsonNode node : root) {
                String name = node.get("name").asText();

                JsonNode commitNode = node.get("commit");
                String commitUrl = commitNode != null ? commitNode.get("url").asText() : null;

                String lastCommitDate = "Unknown";

                if (commitUrl != null) {
                    lastCommitDate = getCommitDate(commitUrl);
                }

                branches.add(new BranchInfo(name, lastCommitDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return branches;
    }

    private String getCommitDate(String commitUrl) {
        try {
            Request request = new Request.Builder()
                    .url(commitUrl)
                    .header("Authorization", "token " + token)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    JsonNode commitDetails = mapper.readTree(response.body().string());

                    JsonNode committerNode = commitDetails.get("commit") != null ? commitDetails.get("commit").get("committer") : null;
                    if (committerNode != null && committerNode.get("date") != null) {
                        return committerNode.get("date").asText();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Unknown";
    }
}
