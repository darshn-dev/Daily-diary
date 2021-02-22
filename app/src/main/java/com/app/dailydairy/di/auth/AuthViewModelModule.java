package com.app.dailydairy.di.auth;


import androidx.lifecycle.ViewModel;

import com.app.dailydairy.di.ViewModelKey;
import com.app.dailydairy.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(AuthViewModel.class)
        public abstract ViewModel bindViewModel(AuthViewModel authViewModel);

}
