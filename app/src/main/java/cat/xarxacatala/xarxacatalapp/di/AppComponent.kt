package cat.xarxacatala.xarxacatalapp.di

import cat.xarxacatala.xarxacatalapp.MainActivity
import cat.xarxacatala.xarxacatalapp.cast.CastFragment
import cat.xarxacatala.xarxacatalapp.di.modules.AppModule
import cat.xarxacatala.xarxacatalapp.showDetail.ShowDetailFragment
import cat.xarxacatala.xarxacatalapp.shows.ShowsFragment
import cat.xarxacatala.xarxacatalapp.videoPlayer.VideoPlayerFragment
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
    fun inject(showDetailFragment: ShowDetailFragment)
    fun inject(videoPlayerFragment: VideoPlayerFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(castFragment: CastFragment)
}