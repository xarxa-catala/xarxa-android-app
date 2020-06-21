package cat.xarxacatalapp.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cat.xarxacatalapp.core.models.Episode

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repos: Episode)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(shows: List<Episode>)

    //FIXME: Once the API implements  it, this should filter by PlaylistId
    @Query("SELECT * FROM episode WHERE showId = :showId AND playlistId = :playlistId")
    fun loadEpisodes(showId: Int, playlistId: Int): LiveData<List<Episode>>

    @Query("SELECT * FROM episode WHERE id = :episodeId")
    fun loadEpisode(episodeId: Int): LiveData<Episode>
}