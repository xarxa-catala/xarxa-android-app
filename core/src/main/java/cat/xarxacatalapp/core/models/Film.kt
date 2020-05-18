package cat.xarxacatalapp.core.models
import com.google.gson.annotations.SerializedName


open class Film(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("show_id")
    val showId: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("year")
    val year: Int
)