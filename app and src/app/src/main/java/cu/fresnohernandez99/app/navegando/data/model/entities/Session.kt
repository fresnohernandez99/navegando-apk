package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Session(
    @Expose
    @SerializedName("userId")
    val userId: String = "",
)