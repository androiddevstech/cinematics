package com.cinematics.santosh.cinematics.tvseries.tvseries;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.databinding.CommonFragmentItemBinding;
import com.cinematics.santosh.cinematics.ui.ListFragmentController;
import com.cinematics.santosh.networkmodule.service.model.SeriesDetailModel;
import com.cinematics.santosh.networkmodule.service.model.SeriesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class TVSeriesFragment extends ListFragmentController<SeriesModel> {

    private OnFragmentInteractionListener mListener;
    private CommonFragmentItemBinding mBinding;
    private TVSeriesAdapter mAdapter;
    private int mPageNumber;
    private SeriesModel mOldResponse;
    private SeriesDetailModel mOldResponse1;

    public TVSeriesFragment() {
    }

    public static TVSeriesFragment newInstance(String param1, String param2) {
        TVSeriesFragment fragment = new TVSeriesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,R.layout.common_fragment_item,container,false);
        View view = mBinding.getRoot();

        mBinding.swipeRefreshLayout.setColorSchemeColors(0xFFB71C1C, 0xFF1A237E, 0xFF2E7D32);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        mBinding.recyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new TVSeriesAdapter(getContext(),mBinding.recyclerView,this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        establishNetworkCall(++mPageNumber);

        return view;


    }

    private void establishNetworkCall(int pageNumber) {
        mBinding.nowPlayingProgressBar.setVisibility(View.VISIBLE);

        switch (mApiType) {
            case API_ON_THE_AIR_TV:
                mApiClient.getOnTheAirTVSeries(pageNumber).enqueue(this);
                break;

            case API_POPULAR_TV:
                mApiClient.getTopRatedTVSeries(pageNumber).enqueue(this);
                break;

            case API_SEARCH_MOVIE:
//                mApiClient.getMovieSearchResults(getArguments().getString(AppIntentConstants.QUERY_STRING), pageNumber).enqueue(this);
                break;

        }
    }

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
    public void onRefresh() {
        mPageNumber = 0;
        establishNetworkCall(++mPageNumber);

    }

    @Override
    public void onLoadMore() {
        establishNetworkCall(++mPageNumber);
    }

    @Override
    protected void onNetworkResponse(Call<SeriesModel> call, Response<SeriesModel> response) {
        //  CALLED ON SWIPE TO REFRESH OR FIRST TIME LAUNCH
        if (mOldResponse1 == null || mPageNumber == 1) {
            mOldResponse = response.body();
//            List<SeriesDetailModel> resultsList = mOldResponse1;
            mAdapter.setNewAPIResponse(mOldResponse);
//            ((TVSeriesAdapter) (mBinding.recyclerView.getAdapter())).setSeriesResponse(resultsList);
            mAdapter.notifyDataSetChanged();

        }
        //  HANDLES PAGINATION REQUESTS
        else {
            int startIndex = mOldResponse.results.size(), totalItems = response.body().results.size();
            mOldResponse.page = response.body().page;
            mOldResponse.total_results = response.body().total_results;
            mOldResponse.results.addAll(response.body().results);

            mAdapter.setNewAPIResponse(mOldResponse);
            mAdapter.notifyItemRangeInserted(startIndex, totalItems);
        }

        mBinding.nowPlayingProgressBar.setVisibility(View.GONE);
        mBinding.swipeRefreshLayout.setRefreshing(false);
        mAdapter.setLoaded();
    }

    @Override
    protected void onNetworkFailure(Call<SeriesModel> call, Throwable t) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
