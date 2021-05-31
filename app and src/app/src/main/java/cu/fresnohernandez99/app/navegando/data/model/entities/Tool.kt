package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tool(
    @Expose
    @SerializedName("type")
    var type: String = "",

    @Expose
    @SerializedName("data")
    val data: String = ""
)