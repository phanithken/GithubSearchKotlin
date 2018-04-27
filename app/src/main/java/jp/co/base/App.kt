package jp.co.base

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import jp.co.base.di.component.AppComponent
import jp.co.base.di.component.DaggerAppComponent
import jp.co.base.di.module.AppModule

class App: Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        Fresco.initialize(this)
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}