package com.cinematics.santosh.cinematics.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.cinematics.santosh.cinematics.enums.MovieDBNetworkAPIEnum;
import com.cinematics.santosh.networkmodule.pojos.constants.AppIntentConstants;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.RetrofitClient;
import com.cinematics.santosh.networkmodule.pojos.retrofitclient.networkwrappers.NetworkFragment;

/**
 * Created by santosh on 2/10/17.
 */

public abstract class ListFragmentController<APIResponseClass>  extends NetworkFragment<APIResponseClass> implements SwipeRefreshLayout.OnRefreshListener{

    protected MovieDBNetworkAPIEnum mApiType;
    protected RetrofitClient.APIClient mApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiType = (MovieDBNetworkAPIEnum) getArguments().getSerializable(AppIntentConstants.API_REQUEST);
        mApiClient = RetrofitClient.getInstance().getNetworkClient();
    }


}
