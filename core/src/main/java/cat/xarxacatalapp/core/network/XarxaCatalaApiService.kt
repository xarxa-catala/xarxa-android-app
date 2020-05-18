package cat.xarxacatalapp.core.network


import cat.xarxacatalapp.core.models.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface XarxaCatalaApiService {

    @GET("/shows")
    fun shows(): Observable<List<Show>>

    @GET("/shows/{showId}/seasons/")
    fun getSeasons(@Path("showId") showId: Int): Observable<List<Season>>

    @GET("/shows/{showId}/films/")
    fun getFilms(@Path("showId") showId: Int): Observable<List<Film>>

    @GET("/shows/{showId}/extras/")
    fun getExtras(@Path("showId") showId: Int): Observable<List<Extra>>

    @GET("/shows/{showId}/seasons/{seasonId}/episodes/")
    fun getEpisodes(
        @Path("showId") showId: Int,
        @Path("seasonId") seasonId: Int
    ): Observable<List<Episode>>


}
