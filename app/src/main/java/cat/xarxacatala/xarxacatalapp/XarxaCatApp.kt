package cat.xarxacatala.xarxacatalapp

import android.app.Application
import cat.xarxacatala.xarxacatalapp.di.AppComponent
import cat.xarxacatala.xarxacatalapp.di.DaggerAppComponent
import cat.xarxacatalapp.core.di.CoreComponent
import cat.xarxacatalapp.core.di.DaggerCoreComponent

class XarxaCatApp : Application() {
    lateinit var coreComponent: CoreComponent
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initCoreDependencyInjection()
        initAppDependencyInjection()
    }

    /**
     * Initialize app dependency injection component.
     */
    private fun initAppDependencyInjection() {
        appComponent = DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
    }

    /**
     * Initialize core dependency injection component.
     */
    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .factory().create(this)
    }
}