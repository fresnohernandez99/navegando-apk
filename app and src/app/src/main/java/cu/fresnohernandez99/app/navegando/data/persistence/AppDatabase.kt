package cu.fresnohernandez99.app.navegando.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import cu.fresnohernandez99.app.navegando.data.model.entities.Vote

@Database(entities = [Vote::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun voteDao(): VoteDao
}
