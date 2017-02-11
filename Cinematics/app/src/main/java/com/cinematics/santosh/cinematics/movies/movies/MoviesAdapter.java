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

import com.cinematics.santosh.cinematics.Model.ResultsNowPlaying;
import com.cinematics.santosh.networkmodule.pojos.constants.APIConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.pojos.model.MoviesModel;
import com.squareup.picasso.Picasso;

/**
 * Created by 511470 on 2/9/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private MoviesModel mMoviesResponse;
    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;


    private LaunchActivity.MovieStationOnClickListener movieStationOnClickListener;
    private ArrayList<ResultsNowPlaying> resultsNowPlaying;

    public MoviesAdapter(Context context, ArrayList<ResultsNowPlaying> resultsNowPlaying, LaunchActivity.MovieStationOnClickListener movieStationOnClickListener){
        this.resultsNowPlaying=resultsNowPlaying;
        this.movieStationOnClickListener=movieStationOnClickListener;
        this.mContext = context;
    }

    public MoviesAdapter(Context context,
                  RecyclerView recyclerView
                  ) {

        mContext = context;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    void setNewAPIResponse(MoviesModel response) {
        mMoviesResponse = response;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.cardview_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MoviesModel.Results results = mMoviesResponse.results.get(position);
        holder.results = results;

        Picasso.with(mContext)
                .load(NetworkConstants.IMG_BASE_POSTER_URL + results.poster_path)
                .into(holder.moviePoster);

        Picasso.with(mContext)
                .load(NetworkConstants.IMG_BASE_BACKDROP_URL + results.backdrop_path)
                .into(holder.backdropImage);
        holder.title.setText(results.title);

//        holder.overViewText.setText(APIConstants.getInstance().getMovieGenreList(results.genre_ids, mContext));
        holder.releaseData.setText(results.release_date);

        ObjectAnimator fade = ObjectAnimator.ofFloat(holder.backGroundlayer, View.ALPHA, 1f,.3f);
        fade.setDuration(600);
        fade.setInterpolator(new LinearInterpolator());
        fade.start();

        /*if(resultsNowPlaying!=null) {
            String poster_path = resultsNowPlaying.get(position).getPoster_path();
            String backdrop_path = resultsNowPlaying.get(position).getBackdrop_path();
            String url = Constants.IMAGE_BASE_URL + poster_path;
            String url_backdrop = Constants.IMAGE_BASE_URL + backdrop_path;
            ImageLoader imageLoader = VolleyTon.getInstance().getImageLoader();

            holder.title.setText(results.title);
            holder.releaseData.setText(resultsNowPlaying.get(position).getRelease_date());
            holder.overViewText.setText(resultsNowPlaying.get(position).getOverview());

            holder.backdropImage.setImageUrl(url_backdrop, imageLoader);
            holder.backdropImage.setErrorImageResId(R.drawable.now_playing_place_holder);
            holder.backdropImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieStationOnClickListener.onClick(position,v,resultsNowPlaying.get(position));
                }
            });
            holder.moviePoster.setImageUrl(url,imageLoader);



        }*/
    }

    @Override
    public int getItemCount() {
        return mMoviesResponse != null ? mMoviesResponse.results.size() : 0;
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

        ViewHolder(View itemView) {
            super(itemView);
            backdropImage =(ImageView) itemView.findViewById(R.id.img_backdrop);
            moviePoster =(ImageView) itemView.findViewById(R.id.img_poster);
            releaseData = (TextView) itemView.findViewById(R.id.releaseData);
            title = (TextView)itemView.findViewById(R.id.title);
            overViewText = (TextView) itemView.findViewById(R.id.movie_description);
            backGroundlayer = (View) itemView.findViewById(R.id.vw_blacklayer);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
