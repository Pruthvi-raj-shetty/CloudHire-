package com.example.cloudhire.api;

import com.example.cloudhire.model.ApplicationResponse;
import com.example.cloudhire.model.JobResponse;
import com.example.cloudhire.model.LoginRequest;
import com.example.cloudhire.model.LoginResponse;
import com.example.cloudhire.model.ResumeResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    // =========================
    // AUTH
    // =========================

    @POST("api/auth/login")
    Call<LoginResponse> login(
            @Body LoginRequest request
    );


    // =========================
    // JOBS
    // =========================

    @GET("api/jobs")
    Call<List<JobResponse>> getOpenJobs();


    // =========================
    // APPLICATIONS
    // =========================

    @POST("api/applications/{jobId}")
    Call<ApplicationResponse> applyToJob(
            @Path("jobId") Long jobId
    );

    @GET("api/applications")
    Call<List<ApplicationResponse>> getMyApplications();

    @GET("api/applications/{applicationId}")
    Call<ApplicationResponse> getMyApplicationDetails(
            @Path("applicationId") Long applicationId
    );


    // =========================
    // RESUME
    // =========================

    @Multipart
    @POST("api/resumes/upload")
    Call<ResumeResponse> uploadResume(
            @Part MultipartBody.Part file
    );

    @GET("api/resumes")
    Call<ResumeResponse> getResume();
}