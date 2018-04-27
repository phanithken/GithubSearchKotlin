package jp.co.quad.githubsearchkotlin.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import jp.co.base.di.ActivityScope
import jp.co.base.util.AppSchedulerProvider
import jp.co.quad.githubsearchkotlin.presenter.MainPresenter
import jp.co.quad.githubsearchkotlin.service.GithubService

@Module
class MainActivityModule {
    @Provides
    @ActivityScope
    internal fun providesHomePresenter(api: GithubService, disposable: CompositeDisposable, scheduler: AppSchedulerProvider): MainPresenter =
            MainPresenter(api, disposable, scheduler)
}