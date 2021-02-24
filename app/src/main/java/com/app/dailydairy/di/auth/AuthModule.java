package com.app.dailydairy.di.auth;

import com.app.dailydairy.network.auth.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    static AuthApi provideAuthApi (Retrofit retrofit){
        return  retrofit.create(AuthApi.class);
    }
}
