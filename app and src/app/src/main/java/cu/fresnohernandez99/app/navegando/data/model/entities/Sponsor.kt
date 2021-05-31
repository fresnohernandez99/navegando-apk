package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sponsor(
    @Expose
    @SerializedName("name")
    var name: String = "",

    @Expose
    @SerializedName("url")
    val url: String = "",

    @Expose
    @SerializedName("description")
    val description: String = "",

    @Expose
    @SerializedName("img")
    val img: String = ""

)