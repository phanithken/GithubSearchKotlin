package jp.co.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.co.base.di.component.AppComponent
import jp.co.base.event.DefaultEvent
import jp.co.base.mvp.BasePresenter
import jp.co.base.mvp.BaseView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast

abstract class BaseActivity: AppCompatActivity(), BaseView{

    private var presenter: BasePresenter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityInject()
    }

    protected abstract fun onActivityInject()

    fun getAppcomponent(): AppComponent = App.appComponent

    override fun onError() {
        toast("Something went wrong")
    }

    override fun setPresenter(presenter: BasePresenter<*>) {
        this.presenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        presenter = null
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun defaultSubscriber(event: DefaultEvent){}
}