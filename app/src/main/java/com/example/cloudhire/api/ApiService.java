package com.example.cloudhire.api;

import com.example.cloudhire.model.JobResponse;
import com.example.cloudhire.model.LoginRequest;
import com.example.cloudhire.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("api/jobs")
    Call<List<JobResponse>> getOpenJobs();
}