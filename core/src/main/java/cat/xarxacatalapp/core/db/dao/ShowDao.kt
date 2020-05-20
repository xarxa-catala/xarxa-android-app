package cat.xarxacatalapp.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cat.xarxacatalapp.core.models.Show

@Dao
interface ShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg shows: Show)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(shows: List<Show>)

    @Query("SELECT * FROM Show")
    fun loadAllShows(): LiveData<List<Show>>

    @Query("SELECT * FROM Show WHERE id = :showId")
    fun loadShow(showId: Int): LiveData<Show>
}