package com.cinematics.santosh.cinematics.movies.movies;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cinematics.santosh.cinematics.LaunchActivity;
import com.cinematics.santosh.cinematics.Model.ResultsNowPlaying;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.interfaces.LoadMore;
import com.cinematics.santosh.cinematics.movies.moviedetails.MoreInfoActivity;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2/9/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements View.OnClickListener {

    private List<MoviesModel.Results> mMoviesList;
    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private MoviesModel mMoviesResponse;


    private ArrayList<ResultsNowPlaying> resultsNowPlaying;
    private boolean loading = false;
    private LoadMore loadMoreListener;

    public MoviesAdapter(Context context, ArrayList<ResultsNowPlaying> resultsNowPlaying, LaunchActivity.MovieStationOnClickListener movieStationOnClickListener){
        this.resultsNowPlaying=resultsNowPlaying;
        this.mContext = context;
    }

    public MoviesAdapter(Context context,
                  RecyclerView recyclerView, LoadMore onLoadMoreListener) {

        mContext = context;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        loadMoreListener = onLoadMoreListener;
    }


    void setNewAPIResponse(MoviesModel response) {
        mMoviesResponse = response;
    }

    public void setMoviesResponse(List<MoviesModel.Results> results) {
        this.mMoviesList = results;
        this.notifyDataSetChanged();
    }

    void setLoaded(){
        loading = false;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_item, parent, false));
        viewHolder.context = mContext;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        MoviesModel.Results results = mMoviesResponse.results.get(position);
        holder.results = results;


//        holder.results = mMoviesList.get(position);
        Glide.with(mContext)
                .load(results.poster_path != null ? NetworkConstants.IMG_BASE_POSTER_URL + results.poster_path :
                        NetworkConstants.IMG_BASE_BACKDROP_URL + results.backdrop_path)
                .into(holder.backdropImage);
        holder.title.setText(results.title);
        List<Integer> genreIds = holder.results.genre_ids;
        holder.genreTextView.setText(/*APIConstants.getInstance().getMovieGenreList(genreIds, mContext)*/results.original_language);
        holder.addToFavsImg.setOnClickListener(this);

        ObjectAnimator fade = ObjectAnimator.ofFloat(holder.backGroundlayer, View.ALPHA, 1f,.3f);
        fade.setDuration(600);
        fade.setInterpolator(new LinearInterpolator());
        fade.start();

        if (position + 1 >= mLinearLayoutManager.getItemCount() && !loading && mMoviesResponse.results.size() < mMoviesResponse.total_results) {
            loading = true;
            if(loadMoreListener != null){
                loadMoreListener.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMoviesResponse != null ? mMoviesResponse.results.size() : 0;
    }

    public void updateNowPlaying(ArrayList<ResultsNowPlaying> resultsNowPlaying){
        this.resultsNowPlaying.addAll(resultsNowPlaying);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addToFavImage:
                Toast.makeText(mContext,"Added to My List",Toast.LENGTH_LONG).show();
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView backdropImage;
        private ImageView addToFavsImg;
        private TextView title;
        private View backGroundlayer;
        private MoviesModel.Results results;
        private TextView genreTextView;
        private Context context;

        ViewHolder(View itemView) {
            super(itemView);
            backdropImage =(ImageView) itemView.findViewById(R.id.img_backdrop);
            title = (TextView)itemView.findViewById(R.id.title);
            backGroundlayer = (View)itemView.findViewById(R.id.vw_blacklayer) ;
            addToFavsImg = (ImageView)itemView.findViewById(R.id.addToFavImage);
            genreTextView = (TextView) itemView.findViewById(R.id.genreTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.setTransitionName("selectedContentTransition");
//            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation()
            MoreInfoActivity.startActivityIntent(context,results);
        }
    }
}
