package com.sourcey.intelligentmedicalsystem.news.retrofit;



import com.sourcey.intelligentmedicalsystem.bean.NewsGson;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface ApiService{

    @GET("{type}/")
    Observable <NewsGson> getNewsData(@Path("type") String type, @Query("key") String key, @Query("num") String num, @Query("page") int page);




}
