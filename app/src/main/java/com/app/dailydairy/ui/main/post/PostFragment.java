package com.app.dailydairy.ui.main.post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dailydairy.R;
import com.app.dailydairy.models.Post;
import com.app.dailydairy.ui.main.Resource;
import com.app.dailydairy.util.VerticalSpaceItemDecoration;
import com.app.dailydairy.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostFragment  extends DaggerFragment {
    private static final String TAG = "PostFragment";

    private PostViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    PostRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_post, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        viewModel = ViewModelProviders.of(this, providerFactory).get(PostViewModel.class);
        initRecyclerView();
        subscribeObservers();

    }

    private void subscribeObservers(){
        viewModel.observerPosts().removeObservers(getViewLifecycleOwner());
        viewModel.observerPosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if(listResource!=null){
                    Log.d(TAG, "onChanged: "+listResource.data);

                    switch (listResource.status){
                        case LOADING:{
                            Log.d(TAG, "onChanged: loading");
                        }
                        break;

                        case SUCCESS:{
                            Log.d(TAG, "onChanged: got data");
                            adapter.setPosts(listResource.data);
                        }
                        break;

                        case ERROR:{
                            Log.d(TAG, "onChanged: error"+listResource.message);
                        }

                        break;
                    }
                }
            }
        });

    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpaceItemDecoration decoration= new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }
}
