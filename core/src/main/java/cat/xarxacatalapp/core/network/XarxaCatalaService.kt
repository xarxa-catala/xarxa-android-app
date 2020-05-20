package cat.xarxacatalapp.core.network


import cat.xarxacatalapp.core.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


const val URL_BASE = "api/v1"
const val URL_PARAMS = "?format=json"

interface XarxaCatalaService {

    @GET("$URL_BASE/shows/$URL_PARAMS")
    suspend fun shows(): Response<List<Show>>

    @GET("$URL_BASE/shows/{showId}/seasons/")
    suspend fun getSeasons(@Path("showId") showId: Int): Response<List<Season>>

    @GET("/shows/{showId}/films/")
    suspend fun getFilms(@Path("showId") showId: Int): Response<List<Film>>

    @GET("/shows/{showId}/extras/")
    suspend fun getExtras(@Path("showId") showId: Int): Response<List<Extra>>

    @GET("$URL_BASE/shows/{showId}/seasons/{seasonId}/episodes/$URL_PARAMS")
    suspend fun getEpisodes(
        @Path("showId") showId: Int,
        @Path("seasonId") seasonId: Int
    ): Response<List<Episode>>


}
