package id.code.alpha.cleanarchitecture

import android.app.Application
import id.code.alpha.cleanarchitecture.di.AppComponent
import id.code.alpha.cleanarchitecture.di.DaggerAppComponent
import id.code.alpha.data.di.CoreComponent
import id.code.alpha.data.di.DaggerCoreComponent


class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}