package com.app.dailydiary.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.app.dailydiary.SessionManager;
import com.app.dailydiary.models.User;
import com.app.dailydiary.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";

    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: View model is ready");
    }


    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return  sessionManager.getUser();
    }
    
}
