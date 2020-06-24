package cat.xarxacatalapp.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cat.xarxacatalapp.core.models.Playlist

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist WHERE showId = :showId AND availableForApp = 1")
    fun loadPlaylists(showId: Int): LiveData<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(playlists: List<Playlist>)

}