package cat.xarxacatalapp.core.models
import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("show_id")
    val showId: Int,
    @SerializedName("url")
    val url: String
)