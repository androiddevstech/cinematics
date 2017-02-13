package com.cinematics.santosh.networkmodule.pojos.retrofitclient;

import com.cinematics.santosh.networkmodule.pojos.constants.APIConstants;
import com.cinematics.santosh.networkmodule.pojos.constants.NetworkConstants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by 511470 on 2/12/17.
 */

public class GracenoteRetrofitClient implements Interceptor {

    private static final GracenoteRetrofitClient mGraceNoteRetrofitClient = new GracenoteRetrofitClient();
    private OkHttpClient mOkHttpClient;
    private GraceNoteAPIClient mApiClient;

    private GracenoteRetrofitClient(){

        mOkHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(this)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetworkConstants.GRACENOTE_NETWORK_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(APIConstants.getInstance().getObjectMapper()))
                .client(mOkHttpClient)
                .build();
        mApiClient = retrofit.create(GraceNoteAPIClient.class);

    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("gracenote_api_key",NetworkConstants.GRACENOTE_API_KEY)
                .build();

        request = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }

    public static GracenoteRetrofitClient getInstance(){
        return mGraceNoteRetrofitClient;
    }

    public GraceNoteAPIClient getGraceNoteNetworkAPI(){
        return mApiClient;
    }

    public void cancelAllRequests() {
        mOkHttpClient.dispatcher().cancelAll();
    }


    public interface GraceNoteAPIClient{

    }
}
