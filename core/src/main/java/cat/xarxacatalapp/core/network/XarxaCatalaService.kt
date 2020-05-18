package cat.xarxacatalapp.core.network


import cat.xarxacatalapp.core.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface XarxaCatalaService {

    @GET("api/v1/shows?format=json")
    suspend fun shows(): Response<List<Show>>

    @GET("/shows/{showId}/seasons/")
    suspend fun getSeasons(@Path("showId") showId: Int): Response<List<Season>>

    @GET("/shows/{showId}/films/")
    suspend fun getFilms(@Path("showId") showId: Int): Response<List<Film>>

    @GET("/shows/{showId}/extras/")
    suspend fun getExtras(@Path("showId") showId: Int): Response<List<Extra>>

    @GET("/shows/{showId}/seasons/{seasonId}/episodes/")
    suspend fun getEpisodes(
        @Path("showId") showId: Int,
        @Path("seasonId") seasonId: Int
    ): Response<List<Episode>>


}
