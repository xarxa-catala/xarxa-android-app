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
    fun insert(vararg repos: Show)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: List<Show>)

    @Query("SELECT * FROM show")
    fun loadAllShows(): LiveData<List<Show>>
}