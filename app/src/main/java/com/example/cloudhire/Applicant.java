package com.example.cloudhire;

public class Applicant {

    private String id;
    private String name;
    private String jobTitle;
    private String experience;
    private String skill;
    private String status;
    private String initial;


    public Applicant() {
    }


    public Applicant(
            String id,
            String name,
            String jobTitle,
            String experience,
            String skill,
            String status,
            String initial
    ) {
        this.id = id;
        this.name = name;
        this.jobTitle = jobTitle;
        this.experience = experience;
        this.skill = skill;
        this.status = status;
        this.initial = initial;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getExperience() {
        return experience;
    }

    public void setExperience(
            String experience
    ) {
        this.experience = experience;
    }


    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }
}