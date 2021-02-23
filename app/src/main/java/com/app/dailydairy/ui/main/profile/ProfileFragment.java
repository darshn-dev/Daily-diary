package com.app.dailydairy.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.dailydairy.R;
import com.app.dailydairy.models.User;
import com.app.dailydairy.ui.auth.AuthResource;
import com.app.dailydairy.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;

    private TextView tvName, tvEmail, tvWebsite;
    @Inject
    ViewModelProviderFactory providerFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: executed");
        tvEmail = view.findViewById(R.id.email);
        tvWebsite = view.findViewById(R.id.website);
        tvName = view.findViewById(R.id.username);

        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }


    private void subscribeObservers(){
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource!=null){
                    switch (userAuthResource.status){
                        case AUTHENTICATED:{
                            setUsersData(userAuthResource.data);
                            break;
                        }

                        case ERROR: {
                            setErroData(userAuthResource.message);
                            break;
                        }
                    }

                }
            }
        });
    }

    private void setUsersData(User usersData){
        tvEmail.setText(usersData.getEmail());
        tvName.setText(usersData.getUsername());
        tvWebsite.setText(usersData.getWebsite());

    }

    private void  setErroData(String string){
        tvEmail.setText(string);
        tvName.setText("");
        tvWebsite.setText("");
    }

}
