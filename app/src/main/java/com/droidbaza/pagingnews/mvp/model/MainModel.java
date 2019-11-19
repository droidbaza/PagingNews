package com.droidbaza.pagingnews.mvp.model;

import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.droidbaza.pagingnews.mvp.MainContract;
import com.droidbaza.pagingnews.paging.NewsAdapter;
import com.droidbaza.pagingnews.paging.NewsSourceFactory;
import com.droidbaza.pagingnews.pojo.Article;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainModel implements MainContract.Model {

    private Context context;
    private FragmentActivity activity;

    public MainModel(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;

    }

    @Override
    public void getModelAdapter(OnFinishedListener onFinishedListener) {


        final NewsAdapter adapter = new NewsAdapter(context);

        NewsSourceFactory sourceFactory = new NewsSourceFactory(activity);
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPageSize(5)
                .build();

        Executor executor = Executors.newFixedThreadPool(5);

        LiveData<PagedList<Article>> pagedListLiveData = (new LivePagedListBuilder<Long, Article>(sourceFactory, config))
                .setFetchExecutor(executor)
                .build();

        pagedListLiveData.observe(activity, articles -> {
            adapter.submitList(articles);
            onFinishedListener.onFinished(adapter);
        });

    }

}
