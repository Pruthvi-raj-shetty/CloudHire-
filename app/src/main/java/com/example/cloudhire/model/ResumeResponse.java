package com.example.cloudhire.model;

public class ResumeResponse {

    private Long id;
    private String fileName;
    private String fileUrl;
    private String uploadedAt;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }
}