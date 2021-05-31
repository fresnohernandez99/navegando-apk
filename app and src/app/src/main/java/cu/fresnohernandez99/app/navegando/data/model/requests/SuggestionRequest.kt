package cu.fresnohernandez99.app.navegando.data.model.requests

import com.google.gson.annotations.SerializedName

data class SuggestionRequest(
    @SerializedName("userId")
    var userId: String = "",
    @SerializedName("text")
    var text: String = ""
)
