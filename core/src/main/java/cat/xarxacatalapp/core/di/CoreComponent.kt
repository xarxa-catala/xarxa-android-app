package cat.xarxacatalapp.core.di

import android.content.Context
import cat.xarxacatalapp.core.db.dao.ShowDao
import cat.xarxacatalapp.core.network.XarxaCatalaService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreModule::class
    ]
)
interface CoreComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun showDao(): ShowDao

    fun xarxaCatalaService(): XarxaCatalaService
}