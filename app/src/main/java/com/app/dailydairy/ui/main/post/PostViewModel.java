package com.app.dailydairy.ui.main.post;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.app.dailydairy.SessionManager;
import com.app.dailydairy.models.Post;
import com.app.dailydairy.network.main.MainApi;
import com.app.dailydairy.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostViewModel extends ViewModel {
    private static final String TAG = "PostViewModel";

    SessionManager sessionManager;
    MainApi mainApi;


    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public PostViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        Log.d(TAG, "PostViewModel: is created");
    }


    public LiveData<Resource<List<Post>>> observerPosts(){
        if(posts == null){
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>)null));
            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostFromUser(sessionManager.getUser().getValue().data.getId()).onErrorReturn(new Function<Throwable, List<Post>>() {
                        @NonNull
                        @Override
                        public List<Post> apply(@NonNull Throwable throwable) throws Exception {
                            Log.d(TAG, "apply: ",throwable);
                            Post post= new Post();
                            post.setId(-1);
                            ArrayList<Post> posts = new ArrayList<>();
                            posts.add(post);
                            return posts;
                        }
                    }).map(new Function<List<Post>, Resource<List<Post>>>() {
                        @NonNull
                        @Override
                        public Resource<List<Post>> apply(@NonNull List<Post> posts) throws Exception {
                            if(posts.size()>0){
                                if (posts.get(0).getId() == -1){
                                    return Resource.error("Error",null);
                                }

                                return Resource.success(posts);
                            }
                            return null;
                        }
                    }).subscribeOn(Schedulers.io())


            );

            posts.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }
        return posts;

    }
}
