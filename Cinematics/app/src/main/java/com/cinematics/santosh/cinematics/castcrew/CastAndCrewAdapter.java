package com.cinematics.santosh.cinematics.castcrew;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;
import com.cinematics.santosh.networkmodule.pojos.model.MoviesTVCastingModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by santosh on 2/13/17.
 */

public class CastAndCrewAdapter extends RecyclerView.Adapter<CastAndCrewAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<MoviesTVCastingModel.Cast> mCastingList;

    public CastAndCrewAdapter(Context context){
        this.mContext = context;
    }

    public void setCastAndCrewResponse(List<MoviesTVCastingModel.Cast> castingList) {
        this.mCastingList = castingList;
        this.notifyDataSetChanged();
    }

    @Override
    public CastAndCrewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CastAndCrewAdapter.RecyclerViewHolder viewHolder = new CastAndCrewAdapter.RecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cast_layout_item, parent, false));
        viewHolder.context = mContext;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CastAndCrewAdapter.RecyclerViewHolder holder, int position) {
        holder.castingResults = mCastingList.get(position);
        String profilePath = holder.castingResults.profile_path;
        if(profilePath != null){
            holder.placeHolder.setVisibility(View.GONE);
            Picasso.with(mContext)
                    .load(NetworkConstants.IMG_BASE_POSTER_URL + holder.castingResults.profile_path)
                    .into(holder.castImage);
        }else {
            holder.placeHolder.setVisibility(View.VISIBLE);
            holder.placeHolder.setText("CB");
        }

        holder.castName.setText(holder.castingResults.name);
        holder.characterName.setText(holder.castingResults.character);
    }

    @Override
    public int getItemCount() {
        return mCastingList != null ? mCastingList.size() : 0;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView castImage;
        private TextView castName;
        private TextView characterName;
        private Context context;
        private MoviesTVCastingModel.Cast castingResults;
        private TextView placeHolder;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            castImage = (ImageView) itemView.findViewById(R.id.castImage);
            castName = (TextView) itemView.findViewById(R.id.castName);
            characterName = (TextView) itemView.findViewById(R.id.characterName);
            placeHolder = (TextView) itemView.findViewById(R.id.circularimgPlaceholder);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //TODO: OPEN CELEBRITY PAGE
        }
    }
}
