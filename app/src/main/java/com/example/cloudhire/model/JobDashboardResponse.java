package com.example.cloudhire.model;

import java.util.List;

public class JobDashboardResponse {

    private List<JobResponse> recentJobs;
    private List<String> companies;
    private List<String> trendingSkills;

    public List<JobResponse> getRecentJobs() {
        return recentJobs;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public List<String> getTrendingSkills() {
        return trendingSkills;
    }
}