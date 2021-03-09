package com.app.dailydiary.di.auth;


import androidx.lifecycle.ViewModel;

import com.app.dailydiary.di.ViewModelKey;
import com.app.dailydiary.ui.auth.AuthViewModel;

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
