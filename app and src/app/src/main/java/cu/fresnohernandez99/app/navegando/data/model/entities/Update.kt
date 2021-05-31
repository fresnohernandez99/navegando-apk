package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Update (
    @Expose
    @SerializedName("version")
    val version: String = "",

    @Expose
    @SerializedName("description")
    val description: String = "",

)