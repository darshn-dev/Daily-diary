package com.app.dailydairy.ui.main.post;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.app.dailydairy.SessionManager;
import com.app.dailydairy.network.main.MainApi;

import javax.inject.Inject;

public class PostViewModel extends ViewModel {
    private static final String TAG = "PostViewModel";

    SessionManager sessionManager;
    MainApi mainApi;

    @Inject
    public PostViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        Log.d(TAG, "PostViewModel: is created");
    }
}
