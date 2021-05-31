package cu.fresnohernandez99.app.navegando.data.model.requests

import com.google.gson.annotations.SerializedName
import cu.fresnohernandez99.app.navegando.data.model.entities.Vote

data class UpdateRecordRequest(
    @SerializedName("userId")
    var userId: String = "",

    @SerializedName("course")
    var course: List<Vote>
)
