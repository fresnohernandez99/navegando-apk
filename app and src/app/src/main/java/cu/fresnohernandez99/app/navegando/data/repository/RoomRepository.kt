package cu.fresnohernandez99.app.navegando.data.repository

import cu.fresnohernandez99.app.navegando.data.model.entities.Vote
import cu.fresnohernandez99.app.navegando.data.persistence.VoteDao
import javax.inject.Inject

class RoomRepository @Inject constructor(private val voteDao: VoteDao) : Repository {

    suspend fun insertVote(newVote: Vote) = voteDao.insertVote(newVote)

    suspend fun getVotes() = voteDao.getVotes()
}