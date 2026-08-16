package com.example.cloudhire;

import java.util.ArrayList;
import java.util.List;

public class RecruiterDashboardData {

    private String recruiterName;

    private int jobsCount;
    private int applicantsCount;
    private int shortlistedCount;
    private int interviewsCount;

    private int unreadNotifications;

    private List<Job> jobs;
    private List<Applicant> applicants;
    private List<Interview> interviews;


    public RecruiterDashboardData() {

        recruiterName = "";

        jobsCount = 0;
        applicantsCount = 0;
        shortlistedCount = 0;
        interviewsCount = 0;

        unreadNotifications = 0;

        jobs = new ArrayList<>();
        applicants = new ArrayList<>();
        interviews = new ArrayList<>();
    }


    public String getRecruiterName() {
        return recruiterName;
    }

    public void setRecruiterName(String recruiterName) {
        this.recruiterName = recruiterName;
    }


    public int getJobsCount() {
        return jobsCount;
    }

    public void setJobsCount(int jobsCount) {
        this.jobsCount = jobsCount;
    }


    public int getApplicantsCount() {
        return applicantsCount;
    }

    public void setApplicantsCount(int applicantsCount) {
        this.applicantsCount = applicantsCount;
    }


    public int getShortlistedCount() {
        return shortlistedCount;
    }

    public void setShortlistedCount(int shortlistedCount) {
        this.shortlistedCount = shortlistedCount;
    }


    public int getInterviewsCount() {
        return interviewsCount;
    }

    public void setInterviewsCount(int interviewsCount) {
        this.interviewsCount = interviewsCount;
    }


    public int getUnreadNotifications() {
        return unreadNotifications;
    }

    public void setUnreadNotifications(
            int unreadNotifications
    ) {
        this.unreadNotifications =
                unreadNotifications;
    }


    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }


    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(
            List<Applicant> applicants
    ) {
        this.applicants = applicants;
    }


    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(
            List<Interview> interviews
    ) {
        this.interviews = interviews;
    }
}