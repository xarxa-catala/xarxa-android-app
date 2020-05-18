package cat.xarxacatala.xarxacatalapp.di

import cat.xarxacatala.xarxacatalapp.shows.ShowsFragment
import cat.xarxacatalapp.core.di.CoreComponent
import cat.xarxacatalapp.core.di.scopes.AppScope
import dagger.Component

@AppScope
@Component(
    dependencies = [
        CoreComponent::class
    ],
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(showsFragment: ShowsFragment)
}