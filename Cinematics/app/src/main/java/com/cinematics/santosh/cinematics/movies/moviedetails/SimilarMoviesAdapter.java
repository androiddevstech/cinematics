package com.cinematics.santosh.cinematics.movies.moviedetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;
import com.cinematics.santosh.networkmodule.service.model.TrailerModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by santosh on 2/13/17.
 */

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.RecyclerViewHolder> {
    private List<MoviesModel.Results> mMoviesList;
    private Context mContext;
    private List<TrailerModel.Results> mTrailerList;

    public SimilarMoviesAdapter(Context context){
        this.mContext = context;
    }

    public void setSimilarMoviesResponse(List<MoviesModel.Results> results) {
        this.mMoviesList = results;
        this.notifyDataSetChanged();
    }

    @Override
    public SimilarMoviesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recommendations_cardview_item, parent, false));
        viewHolder.context = mContext;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimilarMoviesAdapter.RecyclerViewHolder holder, int position) {
        holder.results = mMoviesList.get(position);
        Glide.with(mContext)
                .load(NetworkConstants.IMG_SIMILAR_ITEMS_POSTER_URL + mMoviesList.get(position).poster_path)
                .placeholder(R.drawable.now_playing_place_holder)
                .error(R.drawable.now_playing_place_holder)
                .into(holder.networkImageView);
    }

    @Override
    public int getItemCount() {
        return mMoviesList != null ? mMoviesList.size() : 0;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView networkImageView;
        private Context context;
        private MoviesModel.Results results;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            networkImageView = (ImageView) itemView.findViewById(R.id.networkImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MoreInfoActivity.startActivityIntent(context, results);
//            ((AppCompatActivity) context).finish();
        }
    }
}
