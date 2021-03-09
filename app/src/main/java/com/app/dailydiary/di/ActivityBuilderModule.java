package com.app.dailydiary.di;

import com.app.dailydiary.di.auth.AuthModule;
import com.app.dailydiary.di.auth.AuthScope;
import com.app.dailydiary.di.auth.AuthViewModelModule;
import com.app.dailydiary.di.main.MainFragmentBuilderModule;
import com.app.dailydiary.di.main.MainModule;
import com.app.dailydiary.di.main.MainScope;
import com.app.dailydiary.di.main.MainViewModelModule;
import com.app.dailydiary.ui.auth.AuthActivity;
import com.app.dailydiary.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(modules = {AuthViewModelModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {MainFragmentBuilderModule.class, MainViewModelModule.class, MainModule.class})
    abstract MainActivity contribueMainActivity();

}
