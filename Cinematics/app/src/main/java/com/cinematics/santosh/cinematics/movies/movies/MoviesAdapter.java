package com.cinematics.santosh.cinematics.movies.movies;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.cinematics.santosh.cinematics.LaunchActivity;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.Util.Constants;
import com.cinematics.santosh.cinematics.Util.VolleyTon;

import java.util.ArrayList;
import java.util.List;

import com.cinematics.santosh.cinematics.Model.ResultsNowPlaying;
import com.cinematics.santosh.cinematics.interfaces.LoadMore;
import com.cinematics.santosh.cinematics.movies.moviedetails.MoreInfoActivity;
import com.cinematics.santosh.networkmodule.pojos.constants.APIConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.pojos.model.MoviesModel;
import com.squareup.picasso.Picasso;

/**
 * Created by 511470 on 2/9/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private MoviesModel mMoviesResponse;
    private List<MoviesModel.Results> mMoviesList;
    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;


    private LaunchActivity.MovieStationOnClickListener movieStationOnClickListener;
    private ArrayList<ResultsNowPlaying> resultsNowPlaying;
    private boolean loading = false;
    private LoadMore loadMoreListener;

    public MoviesAdapter(Context context, ArrayList<ResultsNowPlaying> resultsNowPlaying, LaunchActivity.MovieStationOnClickListener movieStationOnClickListener){
        this.resultsNowPlaying=resultsNowPlaying;
        this.movieStationOnClickListener=movieStationOnClickListener;
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

    public void setSimilarMoviesResponse(List<MoviesModel.Results> results) {
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
//        MoviesModel.Results results = mMoviesResponse.results.get(position);
//        holder.results = results;
        holder.results = mMoviesList.get(position);

        /*Picasso.with(mContext)
                .load(NetworkConstants.IMG_BASE_POSTER_URL + results.poster_path)
                .into(holder.moviePoster);*/
        Picasso.with(mContext)
                .load(holder.results.backdrop_path != null ? NetworkConstants.IMG_BASE_BACKDROP_URL + holder.results.backdrop_path :
                        NetworkConstants.IMG_BASE_POSTER_URL + holder.results.poster_path)
                .into(holder.backdropImage);
        holder.title.setText(holder.results.title);

//        holder.overViewText.setText(APIConstants.getInstance().getMovieGenreList(results.genre_ids, mContext));
//        holder.releaseData.setText(results.release_date);

//        holder.title.setText(APIConstants.getInstance().getMovieGenreList(results.genre_ids, mContext));
        ObjectAnimator fade = ObjectAnimator.ofFloat(holder.backGroundlayer, View.ALPHA, 1f,.3f);
        fade.setDuration(600);
        fade.setInterpolator(new LinearInterpolator());
        fade.start();

        if (position + 1 >= mLinearLayoutManager.getItemCount() && !loading && mMoviesList.size() < 1500) {
            loading = true;
            if(loadMoreListener != null){
                loadMoreListener.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMoviesList != null ? mMoviesList.size() : 0;
    }

    public void updateNowPlaying(ArrayList<ResultsNowPlaying> resultsNowPlaying){
        this.resultsNowPlaying.addAll(resultsNowPlaying);
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView backdropImage;
        private ImageView moviePoster;
        private TextView title;
        private TextView releaseData;
        private TextView overViewText;
        private ImageView overFlow;
        private View backGroundlayer;
        private MoviesModel.Results results;
        private Context context;

        ViewHolder(View itemView) {
            super(itemView);
            backdropImage =(ImageView) itemView.findViewById(R.id.img_backdrop);
            moviePoster =(ImageView) itemView.findViewById(R.id.img_poster);
            releaseData = (TextView) itemView.findViewById(R.id.releaseData);
            title = (TextView)itemView.findViewById(R.id.title);
            overViewText = (TextView) itemView.findViewById(R.id.movie_description);
            backGroundlayer = (View)itemView.findViewById(R.id.vw_blacklayer) ;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            MoreInfoActivity.startActivityIntent(context,results);
        }
    }
}
