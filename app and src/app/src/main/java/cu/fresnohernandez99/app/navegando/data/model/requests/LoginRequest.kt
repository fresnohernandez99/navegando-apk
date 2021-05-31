package cu.fresnohernandez99.app.navegando.data.model.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("identifier")
    var identifier: String = ""
)
