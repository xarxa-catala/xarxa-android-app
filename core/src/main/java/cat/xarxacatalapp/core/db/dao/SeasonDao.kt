package cat.xarxacatalapp.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cat.xarxacatalapp.core.models.Season

@Dao
interface SeasonDao {
    @Query("SELECT * FROM season WHERE showId = :showId")
    fun loadSeasons(showId: Int): LiveData<List<Season>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(seasons: List<Season>)

}