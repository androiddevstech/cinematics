package com.cinematics.santosh.cinematics.tvseries.tvseries;

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

import com.bumptech.glide.Glide;
import com.cinematics.santosh.cinematics.LaunchActivity;
import com.cinematics.santosh.cinematics.Model.ResultsNowPlaying;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.interfaces.LoadMore;
import com.cinematics.santosh.cinematics.movies.movies.MoviesAdapter;
import com.cinematics.santosh.cinematics.tvseries.tvseriesmoreinfo.TVSeriesMoreInfoActivity;
import com.cinematics.santosh.networkmodule.service.constants.APIConstants;
import com.cinematics.santosh.networkmodule.service.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;
import com.cinematics.santosh.networkmodule.service.model.SeriesDetailModel;
import com.cinematics.santosh.networkmodule.service.model.SeriesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 511470 on 2/16/17.
 */

public class TVSeriesAdapter extends RecyclerView.Adapter<TVSeriesAdapter.RecyclerViewHolder> {

    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private SeriesModel mSeriesDetailResponse;

    private boolean loading = false;
    private LoadMore loadMoreListener;

    public TVSeriesAdapter(Context context, RecyclerView recyclerView, LoadMore onLoadMoreListener){
        this.mContext = context;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        loadMoreListener = onLoadMoreListener;
    }

    void setNewAPIResponse(SeriesModel response) {
        mSeriesDetailResponse = response;
    }

    void setLoaded(){
        loading = false;
    }

    @Override
    public TVSeriesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_item, parent, false));
        viewHolder.context = mContext;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TVSeriesAdapter.RecyclerViewHolder holder, int position) {

        holder.results = mSeriesDetailResponse.results.get(position);

        Glide.with(mContext)
                .load(holder.results.poster_path != null ? NetworkConstants.IMG_BASE_POSTER_URL + holder.results.poster_path :
                        NetworkConstants.IMG_BASE_BACKDROP_URL + holder.results.backdrop_path)
                .into(holder.backdropImage);
        holder.title.setText(holder.results.name);
        holder.genreTextView.setText(APIConstants.getInstance().getMovieGenreList(holder.results.genre_ids, holder.context));

        ObjectAnimator fade = ObjectAnimator.ofFloat(holder.backGroundlayer, View.ALPHA, 1f,.3f);
        fade.setDuration(600);
        fade.setInterpolator(new LinearInterpolator());
        fade.start();

        if (position + 1 >= mLinearLayoutManager.getItemCount() && !loading && mSeriesDetailResponse.results.size() < mSeriesDetailResponse.total_results) {
            loading = true;
            if(loadMoreListener != null){
                loadMoreListener.onLoadMore();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mSeriesDetailResponse != null ? mSeriesDetailResponse.results.size() : 0;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView backdropImage;
        private TextView title;
        private View backGroundlayer;
        private SeriesModel.Results results;
        private TextView genreTextView;
        private Context context;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            backdropImage =(ImageView) itemView.findViewById(R.id.img_backdrop);
            title = (TextView)itemView.findViewById(R.id.title);
            backGroundlayer = (View)itemView.findViewById(R.id.vw_blacklayer) ;
            genreTextView = (TextView) itemView.findViewById(R.id.genreTextView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //TODO: Series moreinfo goes here
            TVSeriesMoreInfoActivity.startActivityIntent(context,results);
        }
    }

}
