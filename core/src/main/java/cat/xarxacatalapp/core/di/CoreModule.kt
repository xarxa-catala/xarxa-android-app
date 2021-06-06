package cat.xarxacatalapp.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import cat.xarxacatalapp.core.db.XarxaCatDb
import cat.xarxacatalapp.core.db.dao.EpisodeDao
import cat.xarxacatalapp.core.db.dao.PlaylistDao
import cat.xarxacatalapp.core.db.dao.ShowDao
import cat.xarxacatalapp.core.downloads.DownloadStateNotifier
import cat.xarxacatalapp.core.downloads.ProgressResponseBody
import cat.xarxacatalapp.core.network.XarxaCatalaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideOhHttpBuilder(downloadStateNotifier: DownloadStateNotifier): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        // You might want to increase the timeout
        httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.addInterceptor { chain ->
            val progressListener = downloadStateNotifier.getAttachmentDownloadListener(
                chain.request().url().toString()
            )

            val originalResponse: Response = chain.proceed(chain.request())
            originalResponse.newBuilder()
                .body(ProgressResponseBody(originalResponse.body(), progressListener))
                .build()
        }
        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideXarxaCatalaService(okHttpClient: OkHttpClient): XarxaCatalaService {
        return Retrofit.Builder()
            .baseUrl("https://gestio.multimedia.xarxacatala.cat/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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
}