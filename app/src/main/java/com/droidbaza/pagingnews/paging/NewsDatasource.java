package com.droidbaza.pagingnews.paging;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.paging.PageKeyedDataSource;

import com.droidbaza.pagingnews.network.ApiClient;
import com.droidbaza.pagingnews.network.NewsApi;
import com.droidbaza.pagingnews.pojo.Article;
import com.droidbaza.pagingnews.pojo.News;

import java.util.List;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.droidbaza.pagingnews.constants.Constants.API_KEY;


public class NewsDatasource extends PageKeyedDataSource<Long, Article> {

    private NewsApi newsApi;
    private FragmentActivity activity;


    NewsDatasource(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long,
                                    Article> callback) {


        newsApi = ApiClient.getApiClient(activity).create(NewsApi.class);
        newsApi.newsPage(API_KEY,1).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<News>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(News news) {
                            List <Article> articles = news.getArticles();
                            callback.onResult(articles,null,(long)1);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });


    }


    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, Article> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params,
                          @NonNull LoadCallback<Long, Article> callback) {


        newsApi = ApiClient.getApiClient(activity).create(NewsApi.class);
        newsApi.newsPage(API_KEY, params.key).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<News>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(News news) {

                            List<Article> articles = news.getArticles();
                            callback.onResult(articles, Long.valueOf(String.valueOf(params.key + 1)));

                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });


    }

}
