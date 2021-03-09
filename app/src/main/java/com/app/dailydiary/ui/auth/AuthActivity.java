package com.app.dailydiary.ui.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.dailydairy.R;
import com.app.dailydiary.models.User;
import com.app.dailydiary.ui.main.MainActivity;
import com.app.dailydiary.viewmodel.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {


    private static final String TAG = "AuthActivity";

    private  AuthViewModel authViewModel;

    private EditText etUserId;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable drawableLogo;

    @Inject
    RequestManager requestManager;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        etUserId =  findViewById(R.id.user_id_input);
        authViewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);
        progressBar = findViewById(R.id.progress_bar);
        setLogo();

        findViewById(R.id.login_button).setOnClickListener(this);
        subscribe();
    }


    void setLogo(){
        requestManager.load(drawableLogo).into((ImageView)findViewById(R.id.login_logo));

    }

    private void subscribe(){
        authViewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case ERROR: showProgress(false);
                            Toast.makeText(AuthActivity.this,userAuthResource.message,Toast.LENGTH_LONG).show(); break;

                        case LOADING: showProgress(true); break;
                        case AUTHENTICATED: {
                            Log.d(TAG, "onChanged: success " + userAuthResource.data.getUsername());
                            showProgress(false);
                            onLoginSucess();
                            break;
                        }
                        case NOT_AUTHENTICATED: showProgress(false); break;
                    }
                }
            }
        });
    }


    private void onLoginSucess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProgress(boolean isVisible){

        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else
            progressBar.setVisibility(View.GONE);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.login_button:
                attemptLogin();
                break;
        }
    }

    public void attemptLogin(){
        if (TextUtils.isEmpty(etUserId.getText().toString())){
            return;
        }
        authViewModel.authenticateUser(Integer.parseInt(etUserId.getText().toString()));
    }

}