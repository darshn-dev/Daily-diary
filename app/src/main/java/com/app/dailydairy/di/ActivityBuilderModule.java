package com.app.dailydairy.di;

import com.app.dailydairy.di.auth.AuthModule;
import com.app.dailydairy.di.auth.AuthViewModelModule;
import com.app.dailydairy.di.main.MainFragmentBuilderModule;
import com.app.dailydairy.di.main.MainModule;
import com.app.dailydairy.di.main.MainViewModelModule;
import com.app.dailydairy.ui.auth.AuthActivity;
import com.app.dailydairy.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = {AuthViewModelModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(modules = {MainFragmentBuilderModule.class, MainViewModelModule.class, MainModule.class})
    abstract MainActivity contribueMainActivity();

}
