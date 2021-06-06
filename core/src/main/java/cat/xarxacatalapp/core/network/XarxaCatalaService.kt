package cat.xarxacatalapp.core.network


import cat.xarxacatalapp.core.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url


const val URL_BASE = "api/v1"

interface XarxaCatalaService {

    @GET("$URL_BASE/shows/")
    suspend fun shows(): Response<List<Show>>

    @GET("$URL_BASE/shows/{showId}/playlists/")
    suspend fun getPlaylists(@Path("showId") showId: Int): Response<List<Playlist>>

    @GET("$URL_BASE/shows/{showId}/films/")
    suspend fun getFilms(@Path("showId") showId: Int): Response<List<Film>>

    @GET("$URL_BASE/shows/{showId}/extras/")
    suspend fun getExtras(@Path("showId") showId: Int): Response<List<Extra>>

    @GET("$URL_BASE/shows/{showId}/playlists/{playlistId}/videos/")
    suspend fun getEpisodes(
        @Path("showId") showId: Int,
        @Path("playlistId") playlistId: Int
    ): Response<List<Episode>>

    @GET
    @Streaming
    suspend fun episodeDownload(@Url episodeUrl: String): Response<ResponseBody>
}
