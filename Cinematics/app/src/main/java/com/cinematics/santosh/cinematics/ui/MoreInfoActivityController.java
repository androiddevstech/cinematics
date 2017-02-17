package com.cinematics.santosh.cinematics.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.castcrew.CastAndCrewAdapter;
import com.cinematics.santosh.cinematics.databinding.ActivityCommonInfoBinding;
import com.cinematics.santosh.cinematics.movies.moviedetails.SimilarMoviesAdapter;
import com.cinematics.santosh.cinematics.trailers.TrailerAdapter;
import com.cinematics.santosh.networkmodule.service.constants.APIConstants;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;
import com.cinematics.santosh.networkmodule.service.model.MoviesTVCastingModel;
import com.cinematics.santosh.networkmodule.service.model.TrailerModel;
import com.cinematics.santosh.networkmodule.service.retrofitclient.networkwrappers.NetworkActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

/**
 * Created by 511470 on 2/10/17.
 */

public abstract class MoreInfoActivityController<APIResponseClass> extends NetworkActivity<APIResponseClass> implements
        View.OnClickListener{

    protected FloatingActionButton mActionButton;
    protected boolean isFavoriteItem;
    private ActivityCommonInfoBinding mBinding;
    protected String mYouTubeKey;

    protected void initActivity(final String title,
                                int totalCount,
                                float rating,
                                String programDescription,
                                String releaseDate,
                                String genre,
                                String posterPath,
                                String backdropPath,
                                float imageAspectRatio){

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
        mBinding.genreText.setText(genre);
        if(Float.toString(rating).equalsIgnoreCase("0.0")){
            mBinding.ratingTextview.setText("Rating N/A");
            mBinding.totalRatingCountText.setVisibility(View.GONE);
            mBinding.ratingBar.setVisibility(View.GONE);

        }else{
            mBinding.ratingBar.setVisibility(View.VISIBLE);
            mBinding.totalRatingCountText.setVisibility(View.VISIBLE);
            mBinding.ratingTextview.setText(Float.toString(rating));
            mBinding.totalRatingCountText.setText("Total: " + Integer.toString(totalCount));
        }
        mBinding.budgetText.setText("Budget:  " + "$ 1,32,9008");
        mBinding.revenueText.setText("Revenue:  " + "$ 2,43,1222");
        mBinding.similarMoviesText.setText("More like: " + title);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mBinding.recommendationsRecyclerView.setLayoutManager(mLayoutManager);
        mBinding.recommendationsRecyclerView.setAdapter(new SimilarMoviesAdapter(this));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.trailerRecyclerView.setLayoutManager(layoutManager);
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
            case R.id.fab:
                //TODO: Add to favorites functionality
                break;
            default:
                establishNetworkCall();

        }
    }

    public String releaseDateFormatter(String releaseDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedDate = null;
        try {
            formattedDate = simpleDateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat.applyPattern("MMM dd yyyy");

        return simpleDateFormat.format(formattedDate);
    }

    public void showLimitedCastAndCrewView(List<MoviesTVCastingModel.Cast> castingList){
        mBinding.castAndCrewRecyclerView.setVisibility(View.VISIBLE);
        ((CastAndCrewAdapter) (mBinding.castAndCrewRecyclerView.getAdapter())).setCastAndCrewResponse(castingList);

    }



    public void hideCastAndCrewView(){
        mBinding.castAndCrewRecyclerView.setVisibility(View.GONE);
    }

    public void showTrailersView(List<TrailerModel.Results> results){
        mBinding.trailerRecyclerView.setVisibility(View.VISIBLE);
        mBinding.trailersTitle.setVisibility(View.VISIBLE);
        ((TrailerAdapter) (mBinding.trailerRecyclerView.getAdapter())).setTrailerResponse(results);
    }

    public void hideTrailersRecyclerView(){
        mBinding.trailerRecyclerView.setVisibility(View.GONE);
        mBinding.trailersTitle.setVisibility(View.GONE);
    }

    public void showRecommendations(List<MoviesModel.Results> results) {
        mBinding.similarMoviesText.setVisibility(View.VISIBLE);
        mBinding.recommendationsRecyclerView.setVisibility(View.VISIBLE);
        ((SimilarMoviesAdapter) (mBinding.recommendationsRecyclerView.getAdapter())).setSimilarMoviesResponse(results);
    }

    public void hideRecyclerView() {
        mBinding.similarMoviesText.setVisibility(View.GONE);
        mBinding.recommendationsRecyclerView.setVisibility(View.GONE);
    }


}
