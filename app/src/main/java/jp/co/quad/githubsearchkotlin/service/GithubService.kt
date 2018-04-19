package jp.co.quad.githubsearchkotlin.service

import io.reactivex.Observable
import jp.co.quad.githubsearchkotlin.model.Model
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("/search/repositories")
    fun searchRepositories(@Query("q") keyword:String):Observable<Model.Result>

    companion object {
        fun create(): GithubService{
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com")
                    .build()
            return retrofit.create(GithubService::class.java)
        }
    }
}