package com.example.cloudhire.api;

import com.example.cloudhire.RecruiterApplicant;
import com.example.cloudhire.model.ApplicationResponse;
import com.example.cloudhire.model.ForgotPasswordRequest;
import com.example.cloudhire.model.JobDashboardResponse;
import com.example.cloudhire.model.JobResponse;
import com.example.cloudhire.model.LoginRequest;
import com.example.cloudhire.model.LoginResponse;
import com.example.cloudhire.model.ResetPasswordRequest;
import com.example.cloudhire.model.ResumeResponse;
import com.example.cloudhire.model.VerifyOtpRequest;
import com.example.cloudhire.model.UpdateProfileRequest;
import com.example.cloudhire.model.UserProfileResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // =========================
    // AUTH
    // =========================

    @POST("api/auth/login")
    Call<LoginResponse> login(
            @Body LoginRequest request
    );

    @POST("api/auth/forgot-password")
    Call<ResponseBody> forgotPassword(
            @Body ForgotPasswordRequest request
    );

    @POST("api/auth/verify-otp")
    Call<ResponseBody> verifyOtp(
            @Body VerifyOtpRequest request
    );

    @POST("api/auth/reset-password")
    Call<ResponseBody> resetPassword(
            @Body ResetPasswordRequest request
    );


    // =========================
    // JOBS
    // =========================

    @GET("api/jobs")
    Call<List<JobResponse>> getOpenJobs();

    @GET("api/jobs/dashboard")
    Call<JobDashboardResponse> getJobDashboard();

    @GET("api/jobs/search")
    Call<List<JobResponse>> searchJobs(
            @Query("keyword") String keyword
    );

    @GET("api/jobs/my-jobs")
    Call<List<JobResponse>> getRecruiterJobs();


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

    @GET("api/applications/recruiter")
    Call<List<RecruiterApplicant>> getRecruiterApplications();


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

    @GET("api/profile")
    Call<UserProfileResponse> getProfile();

    @PUT("api/profile")
    Call<UserProfileResponse> updateProfile(@Body UpdateProfileRequest request);
}