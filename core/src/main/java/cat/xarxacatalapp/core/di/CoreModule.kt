package cat.xarxacatalapp.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import cat.xarxacatalapp.core.db.XarxaCatDb
import cat.xarxacatalapp.core.db.dao.EpisodeDao
import cat.xarxacatalapp.core.db.dao.PlaylistDao
import cat.xarxacatalapp.core.db.dao.ShowDao
import cat.xarxacatalapp.core.network.XarxaCatalaService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CoreModule {
    @Singleton
    @Provides
    fun provideXarxaCatalaService(): XarxaCatalaService {
        return Retrofit.Builder()
            .baseUrl("https://gestio.multimedia.xarxacatala.cat/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(XarxaCatalaService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): XarxaCatDb {
        return Room
            .databaseBuilder(app, XarxaCatDb::class.java, "xarxacat.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideShowDao(db: XarxaCatDb): ShowDao = db.showDao()

    @Singleton
    @Provides
    fun providePlaylistDao(db: XarxaCatDb): PlaylistDao = db.playlistDao()

    @Singleton
    @Provides
    fun provideEpisodeDao(db: XarxaCatDb): EpisodeDao = db.episodeDao()

    @Singleton
    @Provides
    fun provideApplication(context: Context): Application = context as Application
}