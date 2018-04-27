package jp.co.quad.githubsearchkotlin.di

import dagger.Component
import jp.co.base.di.ActivityScope
import jp.co.base.di.component.AppComponent
import jp.co.quad.githubsearchkotlin.MainActivity

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}