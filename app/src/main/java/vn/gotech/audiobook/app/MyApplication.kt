package vn.gotech.audiobook.app

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.google.firebase.analytics.FirebaseAnalytics
import org.apache.commons.io.IOUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import vn.gotech.audiobook.base.common.Const
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.module.apiModule
import vn.gotech.audiobook.base.domain.module.repoModule
import vn.gotech.audiobook.base.domain.module.viewModelModule

open class MyApplication : MultiDexApplication(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this)
        UserDataManager.init(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        try {
            Const.webViewCSS = IOUtils.toString(assets.open("WebViewStyle.css"), "UTF-8")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            koin.loadModules(listOf(apiModule, repoModule, viewModelModule))
        }
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    private fun onAppBackgrounded() {
//        Log.e("MyApplication", "App in background")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    private fun onAppForegrounded() {
//        Log.e("MyApplication", "App in foreground")
//    }
}