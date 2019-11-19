package com.droidbaza.pagingnews.paging;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class NewsSourceFactory extends DataSource.Factory {

    private FragmentActivity activity;
    public NewsSourceFactory(FragmentActivity activity) {
        mutableLiveData = new MutableLiveData<>();
        this.activity = activity;

    }

    public MutableLiveData<NewsDatasource> getMutableLiveData() {
        return mutableLiveData;
    }

    private MutableLiveData<NewsDatasource> mutableLiveData;

    @NonNull
    @Override
    public DataSource create() {
        NewsDatasource newsDatasource = new NewsDatasource(activity);
        mutableLiveData.postValue(newsDatasource);
        return newsDatasource;
    }
}
