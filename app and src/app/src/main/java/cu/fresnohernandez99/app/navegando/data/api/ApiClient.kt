package cu.fresnohernandez99.app.navegando.data.api

import android.util.Log
import cu.fresnohernandez99.app.navegando.data.model.entities.Update
import cu.fresnohernandez99.app.navegando.data.model.requests.LoginRequest
import cu.fresnohernandez99.app.navegando.data.model.requests.SuggestionRequest
import cu.fresnohernandez99.app.navegando.data.model.requests.UpdateRecordRequest
import cu.fresnohernandez99.app.navegando.data.model.responses.BaseResponse
import cu.fresnohernandez99.app.navegando.data.model.responses.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(private val apiService: ApiService) {

    suspend fun checkUpdates(): Response<Update>? {
        try {
            return apiService.checkUpdates()
        } catch (e: Exception) {
            Log.e("ERROR", "Retrofit Exception")
        }
        return null
    }

    //login
    suspend fun login(request: LoginRequest): Response<LoginResponse>? {
        try {
            return apiService.login(request)
        } catch (e: Exception) {
            Log.e("ERROR", "Retrofit Exception")
        }
        return null
    }

    //records data
    suspend fun updateRecord(request: UpdateRecordRequest) {
        try {
           return apiService.updateRecord(request)
        } catch (e: Exception) {
            Log.e("ERROR", "Retrofit Exception")
        }
    }

    //send suggestion
    suspend fun sendSuggestion(request: SuggestionRequest): Response<BaseResponse<Any>>? {
        try {
            return apiService.sendSuggestion(request)
        } catch (e: Exception) {
            Log.e("ERROR", "Retrofit Exception")
        }
        return null
    }
}