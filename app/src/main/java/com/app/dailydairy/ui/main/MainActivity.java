package com.app.dailydairy.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.dailydairy.BaseActivity;
import com.app.dailydairy.R;
import com.app.dailydairy.ui.main.post.PostFragment;
import com.app.dailydairy.ui.main.profile.ProfileFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout : {
                sessionManager.Logout();
                return  true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void testFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new PostFragment()).commit();
    }
}
