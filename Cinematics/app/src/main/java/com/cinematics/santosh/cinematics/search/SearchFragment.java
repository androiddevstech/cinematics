package com.cinematics.santosh.cinematics.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.databinding.CommonFragmentItemBinding;
import com.cinematics.santosh.cinematics.enums.MovieDBNetworkAPIEnum;
import com.cinematics.santosh.cinematics.interfaces.LoadMore;
import com.cinematics.santosh.cinematics.movies.movies.MoviesAdapter;
import com.cinematics.santosh.networkmodule.service.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.service.model.MoviesModel;
import com.cinematics.santosh.networkmodule.service.retrofitclient.RetrofitClient;
import com.cinematics.santosh.networkmodule.service.retrofitclient.networkwrappers.NetworkFragment;

import retrofit2.Call;
import retrofit2.Response;


public class SearchFragment extends NetworkFragment<MoviesModel> implements SwipeRefreshLayout.OnRefreshListener,LoadMore {

    private OnFragmentInteractionListener mListener;
    private CommonFragmentItemBinding mBinding;
    private MoviesAdapter mAdapter;
    private int mPageNumber;

    protected MovieDBNetworkAPIEnum mApiType;
    protected RetrofitClient.APIClient mApiClient;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiType = (MovieDBNetworkAPIEnum) getArguments().getSerializable(AppIntentConstants.QUERY_STRING);
        mApiClient = RetrofitClient.getInstance().getNetworkClient();
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

    private void establishNetworkCall(int pageNumber){
        mBinding.nowPlayingProgressBar.setVisibility(View.VISIBLE);

        switch (mApiType){
            case API_SEARCH_MOVIE:
                mApiClient.getMovieSearchResults(getArguments().getString(AppIntentConstants.QUERY_STRING), pageNumber).enqueue(this);
                break;
            case API_SEARCH_TV:
                mApiClient.getMovieSearchResults(getArguments().getString(AppIntentConstants.QUERY_STRING), pageNumber).enqueue(this);
                break;
            case API_SEARCH_CELEBRITY:
                mApiClient.getMovieSearchResults(getArguments().getString(AppIntentConstants.QUERY_STRING), pageNumber).enqueue(this);
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

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void onNetworkResponse(Call<MoviesModel> call, Response<MoviesModel> response) {

    }

    @Override
    protected void onNetworkFailure(Call<MoviesModel> call, Throwable t) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
