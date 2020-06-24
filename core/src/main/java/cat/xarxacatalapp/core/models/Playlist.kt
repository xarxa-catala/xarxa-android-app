package cat.xarxacatalapp.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Playlist(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("nom")
    val name: String,
    @SerializedName("show_id")
    val showId: Int,
    @SerializedName("app")
    val availableForApp: Boolean
) {
    override fun toString(): String {
        return name
    }
}