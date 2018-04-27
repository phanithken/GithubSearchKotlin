package jp.co.base.di.module

import dagger.Module
import dagger.Provides
import jp.co.quad.githubsearchkotlin.service.GithubService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesEndpoints(retrofit: Retrofit): GithubService = retrofit.create(GithubService::class.java)
}