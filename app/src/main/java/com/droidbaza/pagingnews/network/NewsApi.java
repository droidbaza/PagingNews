package com.droidbaza.pagingnews.network;

import com.droidbaza.pagingnews.pojo.News;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.droidbaza.pagingnews.constants.Constants.NEWS;

public interface NewsApi {
    @GET(NEWS)
    Single<News>newsPage(@Query("apiKey")String apiKey,
            @Query("page") long pageId);
}
