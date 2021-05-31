package cu.fresnohernandez99.app.navegando.prefsstore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val STORE_NAME = "app_navegando"

class PrefsStoreImpl @Inject constructor(@ApplicationContext context: Context) : PrefsStore {

    private val dataStore = context.createDataStore(name = STORE_NAME)

    //FirstTime
    override fun firstTime() = dataStore.data.catch { exception ->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.FIRST_TIME] ?: true }


    override suspend fun setFalseFirstTime() {
        dataStore.edit {
            it[PreferencesKeys.FIRST_TIME] = false
        }
    }
    //endregion

    //LastStatus
    override fun lastState() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.LAST_STATE] ?: 1 }

    override suspend fun increaseLastState() {
        dataStore.edit {
            if (it[PreferencesKeys.LAST_STATE] == null) it[PreferencesKeys.LAST_STATE] = 2
            else
                it[PreferencesKeys.LAST_STATE]?.let { value ->
                    it[PreferencesKeys.LAST_STATE] = value + 1
                }
        }
    }

    override suspend fun resetLastState() {
        dataStore.edit {
            it[PreferencesKeys.LAST_STATE] = 1
        }
    }
    //endregion

    //Username
    override fun username() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.USERNAME] ?: DEFAULT_NULL_STRING }

    override suspend fun setUsername(username: String) {
        dataStore.edit {
            it[PreferencesKeys.USERNAME] = username
        }
    }
    //endregion

    //Session
    override fun sessionUserId() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.SESSION_USER_ID] ?: DEFAULT_NULL_STRING }

    override suspend fun setSessionUserId(username: String) {
        dataStore.edit {
            it[PreferencesKeys.SESSION_USER_ID] = username
        }
    }
    //end region

    //LastStatus
    override fun lastPosition() = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.LAST_POS] ?: 0 }

    override suspend fun setLastPosition(actualPos: Int) {
        dataStore.edit {
            it[PreferencesKeys.LAST_POS] = actualPos
        }
    }

    override suspend fun resetLastPosition() {
        dataStore.edit {
            it[PreferencesKeys.LAST_POS] = 0
        }
    }
    //endregion

    //KEYS
    private object PreferencesKeys {
        val FIRST_TIME = booleanPreferencesKey("FIRST_TIME")
        val LAST_STATE = intPreferencesKey("LAST_STATE")
        val USERNAME = stringPreferencesKey("USERNAME")
        val SESSION_USER_ID = stringPreferencesKey("SESSIONUSERID")
        val LAST_POS = intPreferencesKey("LAST_POSITION")
    }
    //endregion

    companion object {
        const val DEFAULT_NULL_STRING = "__-___null%%%74("
    }

}