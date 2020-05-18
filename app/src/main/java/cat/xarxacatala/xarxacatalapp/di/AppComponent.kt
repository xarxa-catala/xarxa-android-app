package cat.xarxacatala.xarxacatalapp.di

import cat.xarxacatalapp.core.di.CoreComponent
import cat.xarxacatalapp.core.di.scopes.AppScope
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class]
)
interface AppComponent {
    //fun inject(showsFragment: ShowsFragment)
}