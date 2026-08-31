package com.example.cloudhire;

public class Job {

    private String id;
    private String title;
    private String company;
    private String applicantsCountText;
    private String postedText;
    private String status;
    private String location;
    private String type;
    private String experience;
    private String salary;

    public Job() {
    }

    public Job(
            String id,
            String title,
            String company,
            String applicantsCountText,
            String postedText,
            String status,
            String location,
            String type,
            String experience,
            String salary
    ) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.applicantsCountText =
                applicantsCountText;
        this.postedText = postedText;
        this.status = status;
        this.location = location;
        this.type = type;
        this.experience = experience;
        this.salary = salary;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getApplicantsCountText() {
        return applicantsCountText;
    }

    public void setApplicantsCountText(
            String applicantsCountText
    ) {
        this.applicantsCountText =
                applicantsCountText;
    }


    public String getPostedText() {
        return postedText;
    }

    public void setPostedText(String postedText) {
        this.postedText = postedText;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}