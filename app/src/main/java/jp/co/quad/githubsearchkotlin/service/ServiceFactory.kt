package jp.co.quad.githubsearchkotlin.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public class ServiceFactory {
    companion object {
        fun create(): ServiceFactory{
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com")
                    .build()
            return retrofit.create(ServiceFactory::class.java)
        }
    }
}