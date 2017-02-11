package com.cinematics.santosh.cinematics.movies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.enums.MovieDBNetworkAPIEnum;
import com.cinematics.santosh.cinematics.movies.movies.MoviesFragment;
import com.cinematics.santosh.cinematics.ui.TabFragmentController;
import com.cinematics.santosh.networkmodule.pojos.constants.AppIntentConstants;

import java.util.ArrayList;
import java.util.List;

public class MoviesLaunchFragment extends TabFragmentController implements TabFragmentController.OnFragmentInteractionListener  {

    private OnFragmentInteractionListener mListener;

    public MoviesLaunchFragment() {
        // Required empty public constructor
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
        bundle.putSerializable(AppIntentConstants.API_REQUEST, MovieDBNetworkAPIEnum.API_UPCOMING_MOVIES);
        fragment = new MoviesFragment();
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

        bundle = new Bundle();
        bundle.putSerializable(AppIntentConstants.API_REQUEST, MovieDBNetworkAPIEnum.API_POPULAR_MOVIES);
        fragment = new MoviesFragment();
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);

        fragment = new FavoriteMoviesFragment();
        mFragmentList.add(fragment);
        return mFragmentList;
    }

    @Override
    protected String[] getSearchAndToolbarTitle() {
        return getResources().getStringArray(R.array.movie_toolbar_hint_array);
    }

    @Override
    protected int[] getTabDrawableIcon() {
        return new int[]{R.drawable.ic_menu_camera,
                R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_send};
    }


}
