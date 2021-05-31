package cu.fresnohernandez99.app.navegando.prefsstore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun firstTime(): Flow<Boolean>

    suspend fun setFalseFirstTime()

    fun lastState(): Flow<Int>

    suspend fun increaseLastState()

    suspend fun resetLastState()

    fun username(): Flow<String>

    suspend fun setUsername(username: String)

    fun sessionUserId(): Flow<String>

    suspend fun setSessionUserId(username: String)

    suspend fun setLastPosition(actualPos: Int)

    suspend fun resetLastPosition()

    fun lastPosition(): Flow<Int>

}