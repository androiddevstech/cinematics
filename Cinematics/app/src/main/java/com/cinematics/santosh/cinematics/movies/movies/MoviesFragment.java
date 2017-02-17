package com.cinematics.santosh.cinematics.movies.movies;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.databinding.CommonFragmentItemBinding;
import com.cinematics.santosh.cinematics.ui.ListFragmentController;
import com.cinematics.santosh.cinematics.util.ContentFilterUtil;
import com.cinematics.santosh.networkmodule.service.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import retrofit2.Call;
import retrofit2.Response;

public class MoviesFragment extends ListFragmentController<MoviesModel> {

    private OnFragmentInteractionListener mListener;
    private CommonFragmentItemBinding mBinding;
    private MoviesAdapter mAdapter;
    private int mPageNumber;
    private MoviesModel mOldResponse;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.common_fragment_item,container,false);
        View view = mBinding.getRoot();

        mBinding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.DarkOrange), 0xFF1A237E, 0xFF2E7D32);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        mBinding.recyclerView.setLayoutManager(mLayoutManager);



//        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MoviesAdapter(getContext(),mBinding.recyclerView,this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        establishNetworkCall(++mPageNumber);

        return view;
    }

    private void establishNetworkCall(int pageNumber) {
        mBinding.nowPlayingProgressBar.setVisibility(View.VISIBLE);

        switch (mApiType) {
            case API_UPCOMING_MOVIES:
                mApiClient.getUpcomingMovies(pageNumber).enqueue(this);
                break;

            case API_POPULAR_MOVIES:
                mApiClient.getPopularMovies(pageNumber).enqueue(this);
                break;

            case API_SEARCH_MOVIE:
                mApiClient.getMovieSearchResults(getArguments().getString(AppIntentConstants.QUERY_STRING), pageNumber).enqueue(this);
                break;

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

    @Override
    protected void onNetworkResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
        //  CALLED ON SWIPE TO REFRESH OR FIRST TIME LAUNCH
        switch (mApiType){
            case API_POPULAR_MOVIES:

                if (mOldResponse == null || mPageNumber == 1) {
                    mOldResponse = response.body();
                    List<MoviesModel.Results> resultsList = mOldResponse.results;
                    List<MoviesModel.Results> filteredList = new ArrayList<MoviesModel.Results>();
                    for(int i=0 ; i< resultsList.size();i++){
                        String releaseDate = resultsList.get(i).release_date;
                        String year = releaseDate.substring(0, Math.min(releaseDate.length(), 4));
                            filteredList.add(resultsList.get(i));
                    }
//            mAdapter.setNewAPIResponse(mOldResponse);
                    ((MoviesAdapter) (mBinding.recyclerView.getAdapter())).setMoviesResponse(filteredList);
                    mAdapter.notifyDataSetChanged();

                }
                //  HANDLES PAGINATION REQUESTS
                else {
                    int startIndex = mOldResponse.results.size(), totalItems = response.body().results.size();
                    mOldResponse.page = response.body().page;
                    mOldResponse.total_results = response.body().total_results;
                    mOldResponse.results.addAll(response.body().results);

                    List<MoviesModel.Results> resultsList = mOldResponse.results;
                    List<MoviesModel.Results> filteredList = new ArrayList<MoviesModel.Results>();
                    for(int i=0 ; i< resultsList.size();i++){
                        String releaseDate = resultsList.get(i).release_date;
                        String year = releaseDate.substring(0, Math.min(releaseDate.length(), 4));
                            filteredList.add(resultsList.get(i));
                    }

//            mAdapter.setNewAPIResponse(mOldResponse);
                    ((MoviesAdapter) (mBinding.recyclerView.getAdapter())).setMoviesResponse(filteredList);
                    mAdapter.notifyItemRangeInserted(startIndex, totalItems);
                }


                break;
            case API_UPCOMING_MOVIES:


                if (mOldResponse == null || mPageNumber == 1) {
                    mOldResponse = response.body();
                    List<MoviesModel.Results> resultsList = mOldResponse.results;
                    List<MoviesModel.Results> filteredList = new ArrayList<MoviesModel.Results>();
                    for(int i=0 ; i< resultsList.size();i++){
                        String releaseDate = resultsList.get(i).release_date;
                        String year = releaseDate.substring(0, Math.min(releaseDate.length(), 4));
                        if(year.equals("2017")){
                            filteredList.add(resultsList.get(i));
                        }
                    }
//            mAdapter.setNewAPIResponse(mOldResponse);
                    ((MoviesAdapter) (mBinding.recyclerView.getAdapter())).setMoviesResponse(filteredList);
                    mAdapter.notifyDataSetChanged();

                }
                //  HANDLES PAGINATION REQUESTS
                else {
                    int startIndex = mOldResponse.results.size(), totalItems = response.body().results.size();
                    mOldResponse.page = response.body().page;
                    mOldResponse.total_results = response.body().total_results;
                    mOldResponse.results.addAll(response.body().results);

                    List<MoviesModel.Results> resultsList = mOldResponse.results;
                    List<MoviesModel.Results> upcomingMovies = ContentFilterUtil.getInstance().getUpcomingMovies(resultsList,true);

                    ((MoviesAdapter) (mBinding.recyclerView.getAdapter())).setMoviesResponse(upcomingMovies);
                    mAdapter.notifyItemRangeInserted(startIndex, totalItems);
                }


                break;
        }


        mBinding.nowPlayingProgressBar.setVisibility(View.GONE);
        mBinding.swipeRefreshLayout.setRefreshing(false);
        mAdapter.setLoaded();
    }

    @Override
    protected void onNetworkFailure(Call<MoviesModel> call, Throwable t) {
        //TODO: Handle network failure here
    }

    @Override
    public void onRefresh() {
        mPageNumber = 0;
        establishNetworkCall(++mPageNumber);
        Log.e("Movies", "#### establishnetworkcall called from onRefresh "  +  mPageNumber);
    }

    @Override
    public void onLoadMore() {
        establishNetworkCall(++mPageNumber);
        Log.e("Movies", "#### establishnetworkcall called from onLoadMore "  +  mPageNumber);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
