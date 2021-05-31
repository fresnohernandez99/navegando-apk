package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuestionArray(
    @Expose
    @SerializedName("questions")
    var questions: ArrayList<Question>,

    )