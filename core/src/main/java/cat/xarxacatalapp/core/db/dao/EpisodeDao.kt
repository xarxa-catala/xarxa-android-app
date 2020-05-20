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

    @Query("SELECT * FROM episode WHERE showId = :showId AND seasonId = :seasonId")
    fun loadEpisodes(showId: Int, seasonId: Int): LiveData<List<Episode>>
}