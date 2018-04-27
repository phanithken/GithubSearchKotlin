package jp.co.quad.githubsearchkotlin.presenter

import jp.co.base.mvp.BaseView
import jp.co.quad.githubsearchkotlin.model.RepoItem

interface MainView: BaseView {
    fun onSearchResponse(list: List<RepoItem>?)
    fun noResult()
}