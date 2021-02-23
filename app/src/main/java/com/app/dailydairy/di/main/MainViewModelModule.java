package com.app.dailydairy.di.main;

import androidx.lifecycle.ViewModel;

import com.app.dailydairy.di.ViewModelKey;
import com.app.dailydairy.ui.main.post.PostViewModel;
import com.app.dailydairy.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostViewModel(PostViewModel viewModel);
}
