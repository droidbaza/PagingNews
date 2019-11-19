package com.droidbaza.pagingnews.mvp;

import com.droidbaza.pagingnews.paging.NewsAdapter;


public interface MainContract {
    interface MainView {

        void setAdapter(NewsAdapter adapter);

    }

    interface Presenter {
        void getAdapter();

    }

    interface Model{

        interface OnFinishedListener {
            void onFinished(NewsAdapter adapter);
        }

        void getModelAdapter(OnFinishedListener onFinishedListener);

    }

}
