package com.app.dailydairy.di.main;

import com.app.dailydairy.ui.main.post.PostFragment;
import com.app.dailydairy.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contribueProfileFragment();

    @ContributesAndroidInjector
    abstract PostFragment contribuePostFragment();
}
