package cat.xarxacatalapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import cat.xarxacatalapp.core.db.dao.SeasonDao
import cat.xarxacatalapp.core.db.dao.ShowDao
import cat.xarxacatalapp.core.models.Episode
import cat.xarxacatalapp.core.models.Season
import cat.xarxacatalapp.core.models.Show

@Database(
    entities = [
        Show::class,
        Season::class,
        Episode::class
    ],
    version = 1,
    exportSchema = false
)
abstract class XarxaCatDb : RoomDatabase() {
    abstract fun showDao(): ShowDao

    abstract fun seasonDao(): SeasonDao
}