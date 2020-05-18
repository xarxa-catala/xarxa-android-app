package cat.xarxacatalapp.core.network

import cat.xarxacatalapp.core.AppConstants
import cat.xarxacatalapp.core.models.Film
import cat.xarxacatalapp.core.models.Season
import cat.xarxacatalapp.core.models.Show
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ShowsRepository {
    private val api: XarxaCatalaApiService

    // Public methods (account)
    // --------------------------------------------------------------------------------------------

    fun shows(): Observable<List<Show>> {
        return api.shows()
    }

    fun seasons(showId: Int): Observable<List<Season>> {
        return api.getSeasons(showId)
    }

    fun films(showId: Int): Observable<List<Film>> {
        return api.getFilms(showId)
    }

    fun episodes(showId: Int, seasonId: Int): Observable<List<Film>> {
        return api.getFilms(showId)
    }

    init {
        api = setupClient()
    }

    // Private methods
    // --------------------------------------------------------------------------------------------

    /**
     * Setup client
     */
    private fun setupClient(): XarxaCatalaApiService {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(
                AppConstants.CONNECTION__CONNECT_TIMEOUT.toLong(),
                AppConstants.CONNECTION__TIMEOUT_TIME_UNIT
            )
            .readTimeout(
                AppConstants.CONNECTION__READ_TIMEOUT.toLong(),
                AppConstants.CONNECTION__TIMEOUT_TIME_UNIT
            )
            .writeTimeout(
                AppConstants.CONNECTION__WRITE_TIMEOUT.toLong(),
                AppConstants.CONNECTION__TIMEOUT_TIME_UNIT
            )

        val client = clientBuilder.build()

        val gson = GsonConverterFactory.create()

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.XARXA_CATALA_SERVCIE_DOMAIN)
            .addConverterFactory(gson)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit.create(XarxaCatalaApiService::class.java)
    }
}