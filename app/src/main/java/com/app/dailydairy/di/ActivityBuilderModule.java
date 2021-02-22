package com.app.dailydairy.di;

import com.app.dailydairy.di.auth.AuthModule;
import com.app.dailydairy.di.auth.AuthViewModelModule;
import com.app.dailydairy.ui.auth.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = {AuthViewModelModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();


}
