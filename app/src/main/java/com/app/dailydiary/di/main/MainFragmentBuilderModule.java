package com.app.dailydiary.di.main;

import com.app.dailydiary.ui.main.post.PostFragment;
import com.app.dailydiary.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contribueProfileFragment();

    @ContributesAndroidInjector
    abstract PostFragment contribuePostFragment();
}
