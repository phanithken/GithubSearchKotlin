package jp.co.quad.githubsearchkotlin.presenter

import io.reactivex.disposables.CompositeDisposable
import jp.co.base.mvp.BasePresenter
import jp.co.base.util.SchedulerProvider
import jp.co.quad.githubsearchkotlin.service.GithubService
import javax.inject.Inject

class MainPresenter @Inject constructor(var api: GithubService, disposable: CompositeDisposable, scheduler: SchedulerProvider): BasePresenter<MainView>(disposable, scheduler) {
    fun getRepos(searchKey: String){
        disposable.add(api.searchRepositories(searchKey)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(
                        { result ->
                            view?.onSearchResponse(result?.items)
                            if (result.items == null || result.items.isEmpty()){
                                view?.noResult()
                            }
                        },
                        { _ ->
                            view?.onError()
                        }
                )
        )

    }
}