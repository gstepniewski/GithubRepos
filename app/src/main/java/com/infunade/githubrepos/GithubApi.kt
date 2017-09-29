package com.infunade.githubrepos

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun search(@Query("q") query: String, @Query("sort") sort: String = "stars"): Observable<SearchResponse>

}