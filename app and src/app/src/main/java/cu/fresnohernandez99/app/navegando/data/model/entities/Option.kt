package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Option (
    @Expose
    @SerializedName("id")
    var id: Int = 0,

    @Expose
    @SerializedName("content")
    val content: String = "",

)