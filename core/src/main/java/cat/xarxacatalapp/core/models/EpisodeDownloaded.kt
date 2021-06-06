package cat.xarxacatalapp.core.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Episode::class,
            parentColumns = ["id"],
            childColumns = ["episodeId"],
            onDelete = CASCADE
        )
    ]
)
data class EpisodeDownloaded(
    @PrimaryKey
    val episodeId: Int,
    val path: String
)