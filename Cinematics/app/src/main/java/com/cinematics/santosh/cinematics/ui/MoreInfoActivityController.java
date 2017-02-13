package com.cinematics.santosh.cinematics.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.databinding.ActivityCommonInfoBinding;
import com.cinematics.santosh.networkmodule.pojos.constants.APIConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.networkwrappers.NetworkActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 511470 on 2/10/17.
 */

public abstract class MoreInfoActivityController<APIResponseClass> extends NetworkActivity<APIResponseClass> implements
        View.OnClickListener{

    protected FloatingActionButton mActionButton;
    protected boolean isFavoriteItem;
    private ActivityCommonInfoBinding mBinding;

    protected void initActivity(final String title, String programDescription, String releaseDate, String posterPath, String backdropPath, float imageAspectRatio){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_common_info);
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);

        mBinding.movieDetailsBackdrop.requestLayout();
        mBinding.movieDetailsBackdrop.getLayoutParams().height = (int) Math.ceil(APIConstants.getScreenWidthPixels(this) * imageAspectRatio);
        Picasso.with(this)
                .load(NetworkConstants.IMG_BASE_BACKDROP_URL + backdropPath)
                .into(mBinding.movieDetailsBackdrop);
        Picasso.with(this)
                .load(NetworkConstants.IMG_BASE_POSTER_URL + posterPath)
                .into(mBinding.movieDetailsPoster);

        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.movieDetailsBackdrop.setOnClickListener(this);
        mBinding.fab.setOnClickListener(this);

        mBinding.coolapsingToolbarLayout.setTitle(" ");
        mBinding.programTitle.setText(title);
        mBinding.programDescription.setText(programDescription);
        mBinding.releaseDate.setText(releaseDate);
        //-----------------------------------------------
        // HIDING TOOLBAR TITLE WHEN TOOL BAR IS EXPANDED
        //-----------------------------------------------
        mBinding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mBinding.coolapsingToolbarLayout.setTitle(title);
                    isShow = true;
                } else if(isShow) {
                    mBinding.coolapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }

    protected abstract void establishNetworkCall();




    @Override
    protected void onNetworkFailure(Call<APIResponseClass> call, Throwable t) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                //TODO: Add to favorites functionality
                break;
            case R.id.movieDetailsBackdrop:
                //TODO: launch youtube activity
                break;
            default:
                establishNetworkCall();

        }
    }


}
