package com.cinematics.santosh.cinematics.tvseries;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.enums.MovieDBNetworkAPIEnum;
import com.cinematics.santosh.cinematics.tvseries.tvseries.TVSeriesFragment;
import com.cinematics.santosh.cinematics.ui.TabFragmentController;
import com.cinematics.santosh.networkmodule.service.constants.AppIntentConstants;

import java.util.ArrayList;
import java.util.List;

public class TVSeriesLaunchFragment extends TabFragmentController {
    private OnFragmentInteractionListener mListener;

    public TVSeriesLaunchFragment() {
    }



    public static TVSeriesLaunchFragment newInstance(String param1, String param2) {
        TVSeriesLaunchFragment fragment = new TVSeriesLaunchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    protected List<Fragment> getViewPagerFragmentList() {
        List<Fragment> mFragmentList = new ArrayList<>(3);
        Fragment fragment;

        Bundle bundle = new Bundle();
        bundle.putSerializable(AppIntentConstants.API_REQUEST, MovieDBNetworkAPIEnum.API_ON_THE_AIR_TV);
        fragment = new TVSeriesFragment();
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

        bundle = new Bundle();
        bundle.putSerializable(AppIntentConstants.API_REQUEST, MovieDBNetworkAPIEnum.API_POPULAR_TV);
        fragment = new TVSeriesFragment();
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

        fragment = new FavoriteTVSeriesFragment();
        mFragmentList.add(fragment);
        return mFragmentList;
    }

    @Override
    protected String[] getSearchAndToolbarTitle() {
        return getResources().getStringArray(R.array.tv_toolbar_hint_array);
    }

    @Override
    protected int[] getTabDrawableIcon() {
        return new int[]{R.drawable.ic_trending_up_white,
                R.drawable.ic_movie_filter_white,
                R.drawable.ic_favorite_border_white};
    }
}
