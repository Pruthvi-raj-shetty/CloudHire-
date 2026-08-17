package com.example.cloudhire;

public class Interview {

    private String id;
    private String candidateName;
    private String jobTitle;
    private String date;
    private String time;
    private String interviewType;
    private String status;


    public Interview() {
    }


    public Interview(
            String id,
            String candidateName,
            String jobTitle,
            String date,
            String time,
            String interviewType,
            String status
    ) {
        this.id = id;
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.date = date;
        this.time = time;
        this.interviewType = interviewType;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(
            String candidateName
    ) {
        this.candidateName = candidateName;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(
            String jobTitle
    ) {
        this.jobTitle = jobTitle;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(
            String interviewType
    ) {
        this.interviewType = interviewType;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}