package com.droidbaza.pagingnews.mvp.presenter;

import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import com.droidbaza.pagingnews.mvp.MainContract;
import com.droidbaza.pagingnews.mvp.model.MainModel;



public class MainPresenter implements MainContract.Presenter {

    private MainContract.MainView view;
    private Context context;
    private FragmentActivity activity;

    public MainPresenter(MainContract.MainView view,
                         Context context, FragmentActivity activity) {
        this.view = view;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void getAdapter() {
            MainModel mainModel = new MainModel(context,activity);
            mainModel.getModelAdapter(adapter -> view.setAdapter(adapter));
    }
}
