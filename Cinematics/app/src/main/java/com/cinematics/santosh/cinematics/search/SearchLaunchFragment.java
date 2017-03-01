package com.cinematics.santosh.cinematics.search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.enums.MovieDBNetworkAPIEnum;
import com.cinematics.santosh.cinematics.ui.SearchTabFragmentController;
import com.cinematics.santosh.networkmodule.service.constants.AppIntentConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santosh on 2/19/17.
 */

public class SearchLaunchFragment extends SearchTabFragmentController {
    private OnFragmentInteractionListener mListener;
    public SearchLaunchFragment(){

    }

    public static SearchLaunchFragment newInstance(String param1, String param2) {
        SearchLaunchFragment fragment = new SearchLaunchFragment();
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
        if (context instanceof SearchLaunchFragment.OnFragmentInteractionListener) {
            mListener = (SearchLaunchFragment.OnFragmentInteractionListener) context;
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
        bundle.putSerializable(AppIntentConstants.QUERY_STRING, MovieDBNetworkAPIEnum.API_SEARCH_MOVIE);
        fragment = new SearchFragment();
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

        bundle = new Bundle();
        bundle.putSerializable(AppIntentConstants.QUERY_STRING, MovieDBNetworkAPIEnum.API_SEARCH_TV);
        fragment = new SearchFragment();
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

        bundle.putSerializable(AppIntentConstants.QUERY_STRING, MovieDBNetworkAPIEnum.API_SEARCH_CELEBRITY);
        fragment = new SearchFragment();
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

        return mFragmentList;
    }

    @Override
    protected String[] getSearchAndToolbarTitle() {
        return getResources().getStringArray(R.array.search_toolbar_hint_array);
    }

    @Override
    protected int[] getTabDrawableIcon() {
        return new int[]{R.drawable.ic_movie_filter_white,
                R.drawable.ic_tv_white_24dp,
                R.drawable.ic_celebrities};
    }
}
