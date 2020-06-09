package cat.xarxacatalapp.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Show(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("nom")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)