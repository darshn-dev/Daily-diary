package com.app.dailydiary.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.app.dailydiary.SessionManager;
import com.app.dailydiary.models.User;
import com.app.dailydiary.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();
    AuthApi authApi;
    SessionManager sessionManager;
    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;

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

        Log.d(TAG, "authenticateUser: attempting to login");
        sessionManager.authWithID(queryUserId(userID));
    }


    private LiveData<AuthResource<User>> queryUserId(int id){
        return   LiveDataReactiveStreams.fromPublisher(authApi.getUser(id).subscribeOn(Schedulers.io())
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
    }



    public LiveData<AuthResource<User>> observeAuthState(){
        return  sessionManager.getUser();
    }
}
