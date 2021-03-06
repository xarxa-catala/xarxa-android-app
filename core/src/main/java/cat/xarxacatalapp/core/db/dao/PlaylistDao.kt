package cat.xarxacatalapp.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cat.xarxacatalapp.core.models.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist WHERE showId = :showId AND availableForApp = 1 ORDER BY name")
    fun loadPlaylists(showId: Int): Flow<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(playlists: List<Playlist>)

}