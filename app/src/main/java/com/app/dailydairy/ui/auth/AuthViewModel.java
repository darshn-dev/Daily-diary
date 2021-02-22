package com.app.dailydairy.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.app.dailydairy.models.User;
import com.app.dailydairy.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();
    AuthApi authApi;
    @Inject
    public AuthViewModel(AuthApi authApi) {
        this.authApi = authApi;

        Log.d(TAG, "AuthViewModel: --craeted");
//        authApi.getUser(2).toObservable().subscribeOn(Schedulers.io()).subscribe(new Observer<User>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull User user) {
//                Log.d(TAG, "onNext: "+user.getUsername());
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.d(TAG, "onError: "+e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
                
    }

    public void authenticateUser(int userID){

        authUser.setValue(AuthResource.loading((User)null));
        final  LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(authApi.getUser(userID).subscribeOn(Schedulers.io())
        .onErrorReturn(new Function<Throwable, User>() {
            @NonNull
            @Override
            public User apply(@NonNull Throwable throwable) throws Exception {
                User errorUser =new User();
                errorUser.setId(-1);
                return errorUser;
            }
        }).map(new Function<User, AuthResource<User>>() {
                            @NonNull
                            @Override
                            public AuthResource<User> apply(@NonNull User user) throws Exception {
                                if(user.getId()== -1){
                                    return AuthResource.error("Couldn't authenticate", (User)null);
                                }

                                return  AuthResource.authenticated(user);
                            }
                        })
        );

        authUser.addSource(source, new androidx.lifecycle.Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }

    public LiveData<AuthResource<User>> observeUser(){
        return  authUser;
    }
}
