package com.cinematics.santosh.cinematics.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.castcrew.CastAndCrewAdapter;
import com.cinematics.santosh.cinematics.databinding.SeriesCommonInfoBinding;
import com.cinematics.santosh.cinematics.movies.moviedetails.SimilarMoviesAdapter;
import com.cinematics.santosh.cinematics.trailers.TrailerAdapter;
import com.cinematics.santosh.networkmodule.service.constants.APIConstants;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.retrofitclient.networkwrappers.NetworkActivity;

import retrofit2.Call;

/**
 * Created by santosh on 2/16/17.
 */

public abstract class SeriesMoreInfoActivityController<APIResponseClass> extends NetworkActivity<APIResponseClass> implements
        View.OnClickListener{

    private SeriesCommonInfoBinding mBinding;
    protected String mYoutubeKey;

    protected void initAcitivity(final String title,
                                 int totalCount,
                                 float rating,
                                 String seriesDesc,
                                 String releaseDate,
                                 String genre,
                                 String posterPath,
                                 String backdropPath,
                                 float imageAspectRatio){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.series_common_info);
        setSupportActionBar(mBinding.seriestoolbar);
        mBinding.seriestoolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);

        mBinding.seriesDetailsBackdrop.requestLayout();
        mBinding.seriesDetailsBackdrop.getLayoutParams().height = (int) Math.ceil(APIConstants.getScreenWidthPixels(this) * imageAspectRatio);

        Glide.with(this)
                .load(NetworkConstants.IMG_BASE_BACKDROP_URL + backdropPath)
                .into(mBinding.seriesDetailsBackdrop);
        Glide.with(this)
                .load(NetworkConstants.IMG_BASE_POSTER_URL + posterPath)
                .into(mBinding.seriesDetailsPoster);

        mBinding.seriestoolbar.setNavigationOnClickListener(this);
        mBinding.seriesDetailsBackdrop.setOnClickListener(this);

        mBinding.coolapsingToolbarLayout.setTitle(" ");
        mBinding.seriesProgramTitle.setText(title);
        mBinding.programDescription.setText(seriesDesc);
        mBinding.seriesReleaseDate.setText(releaseDate);
        mBinding.seriesGenreText.setText(genre);

        if(Float.toString(rating).equalsIgnoreCase("0.0")){
            mBinding.seriesratingTextview.setText("Rating N/A");
            mBinding.seriesTotalRatingCountText.setVisibility(View.GONE);
            mBinding.seriesRatingBar.setVisibility(View.GONE);

        }else{
            mBinding.seriesRatingBar.setVisibility(View.VISIBLE);
            mBinding.seriesTotalRatingCountText.setVisibility(View.VISIBLE);
            mBinding.seriesratingTextview.setText(Float.toString(rating));
            mBinding.seriesTotalRatingCountText.setText("Total: " + Integer.toString(totalCount));
        }

        mBinding.budgetText.setText("Budget:  " + "$ 1,32,9008");
        mBinding.revenueText.setText("Revenue:  " + "$ 2,43,1222");
        mBinding.similarMoviesText.setText("More like: " + title);

        RecyclerView.LayoutManager similarTVshowsLayout = new GridLayoutManager(this, 3);
        mBinding.recommendationsRecyclerView.setLayoutManager(similarTVshowsLayout);
        mBinding.recommendationsRecyclerView.setAdapter(new SimilarMoviesAdapter(this));

        LinearLayoutManager trialerLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.trailerRecyclerView.setLayoutManager(trialerLayoutManager);
        mBinding.trailerRecyclerView.setAdapter(new TrailerAdapter(this));

        RecyclerView.LayoutManager castAndCrewLayoutManager = new GridLayoutManager(this, 3);
        mBinding.castAndCrewRecyclerView.setLayoutManager(castAndCrewLayoutManager);
        mBinding.castAndCrewRecyclerView.setAdapter(new CastAndCrewAdapter(this));

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
            case R.id.seriestoolbar:
                finish();
                break;
        }

    }


}
