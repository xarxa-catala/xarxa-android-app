package cat.xarxacatalapp.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Episode(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("nom")
    val name: String,
    @SerializedName("playlist_id")
    var playlistId: Int,
//    //FIXME: This shouldn't be ignored
//    @SerializedName("prequels")
//    @Ignore
//    val prequels: List<PreSequel>,
//    @SerializedName("sequels")
//    @Ignore
//    val sequels: List<PreSequel>,

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