package com.cinematics.santosh.cinematics;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cinematics.santosh.cinematics.databinding.ActivitySearchControllerLayoutBinding;
import com.cinematics.santosh.cinematics.search.SearchFragment;
import com.cinematics.santosh.cinematics.search.SearchLaunchFragment;
import com.cinematics.santosh.cinematics.ui.TabFragmentController;

/**
 * Created by santosh on 2/20/17.
 */

public class SearchBaseActivity extends AppCompatActivity implements View.OnClickListener,
SearchLaunchFragment.OnFragmentInteractionListener,
        TabFragmentController.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener{

    ActivitySearchControllerLayoutBinding mBinding;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

    }

    private void bindView(){
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_search_controller_layout);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.search_fragment_container,new SearchLaunchFragment()).commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_back_btn:
                finish();
                break;

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
