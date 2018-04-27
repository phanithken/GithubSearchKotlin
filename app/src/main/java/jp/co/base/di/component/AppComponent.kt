package jp.co.base.di.component

import android.app.Application
import android.content.res.Resources
import com.google.gson.Gson
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import jp.co.base.di.module.ApiModule
import jp.co.base.di.module.AppModule
import jp.co.base.di.module.OkHttpModule
import jp.co.base.di.module.RetrofitModule
import jp.co.base.util.AppSchedulerProvider
import jp.co.quad.githubsearchkotlin.service.GithubService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, RetrofitModule::class, ApiModule::class, OkHttpModule::class))
interface AppComponent {
    fun application(): Application
    fun gson(): Gson
    fun resources(): Resources
    fun retrofit(): Retrofit
    fun githubService(): GithubService
    fun cache(): Cache
    fun client(): OkHttpClient
    fun loggingInterceptor(): HttpLoggingInterceptor
    fun compositeDisposable(): CompositeDisposable
    fun schedulerProvider(): AppSchedulerProvider
}