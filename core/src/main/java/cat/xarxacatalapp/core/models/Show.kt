package cat.xarxacatalapp.core.models
import com.google.gson.annotations.SerializedName


data class Show(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)