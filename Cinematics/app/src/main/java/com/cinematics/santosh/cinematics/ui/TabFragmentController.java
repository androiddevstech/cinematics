package com.cinematics.santosh.cinematics.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinematics.santosh.cinematics.BaseActivity;
import com.cinematics.santosh.cinematics.R;
import com.cinematics.santosh.cinematics.databinding.TabFragmentLayoutBinding;
import com.cinematics.santosh.cinematics.ui.util.BaseSlidingTabs;
import com.cinematics.santosh.networkmodule.service.model.GenericSearchResultsModel;
import com.cinematics.santosh.networkmodule.service.retrofitclient.networkwrappers.NetworkFragment;

import java.util.List;
import java.util.WeakHashMap;

import retrofit2.Call;
import retrofit2.Response;

public abstract class TabFragmentController extends NetworkFragment<GenericSearchResultsModel> implements View.OnClickListener,
        ViewPager.OnPageChangeListener{
    private List<Fragment> mFragmentList;
    private String[] mToolbarTitle;
    private OnFragmentInteractionListener mListener;
    private TabFragmentLayoutBinding mBinding;
    private WeakHashMap<Integer, Fragment> mFragmentMap;
    private BaseActivity mParentActivity;

    public TabFragmentController() {
        // Required empty public constructor
    }

    protected abstract List<Fragment> getViewPagerFragmentList();

    protected abstract String[] getSearchAndToolbarTitle();

    protected abstract int[] getTabDrawableIcon();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentList = getViewPagerFragmentList();
        mToolbarTitle = getSearchAndToolbarTitle();
        mFragmentMap = new WeakHashMap<>(mFragmentList.size());
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,R.layout.tab_fragment_layout, container,false);
        View view = mBinding.getRoot();


        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);
        mBinding.viewPager.setOffscreenPageLimit(mFragmentList.size());
        mBinding.viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        mBinding.slidingTabLayout.setOnPageChangeListener(this);
        mBinding.slidingTabLayout.setViewPager(mBinding.viewPager);
        return view;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            mParentActivity = ((BaseActivity)getActivity());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void onNetworkResponse(Call<GenericSearchResultsModel> call, Response<GenericSearchResultsModel> response) {

    }

    @Override
    protected void onNetworkFailure(Call<GenericSearchResultsModel> call, Throwable t) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            mBinding.toolbar.setTitle(mToolbarTitle[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter implements BaseSlidingTabs.IconTabProvider{

        private int[] mDrawableList;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            mDrawableList = getTabDrawableIcon();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int getPageIconResId(int position) {
            return mDrawableList[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            mFragmentMap.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mFragmentMap.remove(position);
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mParentActivity = null;
    }



}
