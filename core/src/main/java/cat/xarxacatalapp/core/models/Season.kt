package cat.xarxacatalapp.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Season(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("nom")
    val name: String,
    @SerializedName("show_id")
    val showId: Int,
    @SerializedName("url")
    val url: String
)