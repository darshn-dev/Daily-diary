package com.app.dailydiary.network.auth;

import com.app.dailydiary.models.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("/users/{id}")
    Flowable<User> getUser(@Path("id") int id);
}
