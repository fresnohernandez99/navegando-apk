package cu.fresnohernandez99.app.navegando.data.api

import cu.fresnohernandez99.app.navegando.data.URLFactory
import cu.fresnohernandez99.app.navegando.data.model.entities.Update
import cu.fresnohernandez99.app.navegando.data.model.requests.LoginRequest
import cu.fresnohernandez99.app.navegando.data.model.requests.SuggestionRequest
import cu.fresnohernandez99.app.navegando.data.model.requests.UpdateRecordRequest
import cu.fresnohernandez99.app.navegando.data.model.responses.BaseResponse
import cu.fresnohernandez99.app.navegando.data.model.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(URLFactory.update)
    suspend fun checkUpdates(): Response<Update>

    @POST(URLFactory.login)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST(URLFactory.updateRecord)
    suspend fun updateRecord(@Body request: UpdateRecordRequest)

    @POST(URLFactory.sendSuggestion)
    suspend fun sendSuggestion(@Body request: SuggestionRequest): Response<BaseResponse<Any>>

}