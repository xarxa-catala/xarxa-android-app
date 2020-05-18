package cat.xarxacatalapp.core.models
import com.google.gson.annotations.SerializedName


data class Episode(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("season_id")
    val seasonId: Int,
    @SerializedName("prequels")
    val prequels: List<PreSequel>,
    @SerializedName("sequels")
    val sequels: List<PreSequel>,
    @SerializedName("show_id")
    val showId: Int,
    @SerializedName("url")
    val url: String
)

data class PreSequel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)