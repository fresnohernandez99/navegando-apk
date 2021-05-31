package cu.fresnohernandez99.app.navegando.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cu.fresnohernandez99.app.navegando.data.model.entities.Vote

@Dao
interface VoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVote(vote: Vote)

    @Query("SELECT * FROM vote")
    suspend fun getVotes(): List<Vote>

}
