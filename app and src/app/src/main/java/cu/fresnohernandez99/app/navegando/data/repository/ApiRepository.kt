package cu.fresnohernandez99.app.navegando.data.repository

import cu.fresnohernandez99.app.navegando.data.api.ApiClient
import cu.fresnohernandez99.app.navegando.data.model.entities.Vote
import cu.fresnohernandez99.app.navegando.data.model.requests.LoginRequest
import cu.fresnohernandez99.app.navegando.data.model.requests.SuggestionRequest
import cu.fresnohernandez99.app.navegando.data.model.requests.UpdateRecordRequest
import cu.fresnohernandez99.app.navegando.data.model.responses.BaseResponse
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val prefsStore: PrefsStore,
) : Repository {

    suspend fun getUpdates() = apiClient.checkUpdates()

    //Login
    suspend fun login(identifier: String) = apiClient.login(LoginRequest(identifier))

    //update record
    suspend fun updateRecord(votes: List<Vote>) {
        val userId = prefsStore.sessionUserId().first()
        val request = UpdateRecordRequest(userId, votes)
        return apiClient.updateRecord(request)
    }

    //send suggestion
    suspend fun sendSuggestion(text: String): Response<BaseResponse<Any>>? {
        val userId = prefsStore.sessionUserId().first()
        val request = SuggestionRequest(userId, text)
        return apiClient.sendSuggestion(request)
    }
}