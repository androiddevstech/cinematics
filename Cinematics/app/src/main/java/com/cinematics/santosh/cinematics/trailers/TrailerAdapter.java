package com.cinematics.santosh.cinematics.trailers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.ui.MoreInfoActivityController;
import com.cinematics.santosh.networkmodule.pojos.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.pojos.model.TrailerModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 511470 on 2/13/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<TrailerModel.Results> mTrailerList;

    public TrailerAdapter(Context context){
        this.mContext = context;
    }

    public void setTrailerResponse(List<TrailerModel.Results> results) {
        this.mTrailerList = results;
        this.notifyDataSetChanged();
    }


    @Override
    public TrailerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.trailer_carousel_item, parent, false));
        viewHolder.context = mContext;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.RecyclerViewHolder holder, int position) {

        holder.results = mTrailerList.get(position);
        String thumbnailUrl = NetworkConstants.YOUTUBE_THUMBNAIL_BASE_URL + holder.results.key + NetworkConstants.YOUTUBE_THUMBANAIL_QUALITY_BEST;
        Picasso.with(mContext)
                .load(thumbnailUrl)
                .into(holder.youtubeThumbnail);

        holder.trailerTitle.setText(holder.results.name);
    }

    @Override
    public int getItemCount() {
        return mTrailerList != null ? mTrailerList.size() : 0;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView youtubeThumbnail;
        private TextView trailerTitle;
        private Context context;
        private TrailerModel.Results results;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            youtubeThumbnail = (ImageView) itemView.findViewById(R.id.youtubeTrailerThumbnail);
            trailerTitle = (TextView) itemView.findViewById(R.id.trailertitle);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent youtubePlayer = new Intent(context, YoutubePlayerActivity.class);
            youtubePlayer.putExtra(AppIntentConstants.TRAILER_LAUNCH, results.key);
            context.startActivity(youtubePlayer);

        }
    }
}
