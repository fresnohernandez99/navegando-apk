package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Article(
    @Expose
    @SerializedName("id")
    var id: Int = 0,

    @Expose
    @SerializedName("title")
    val title: String = "",

    @Expose
    @SerializedName("description")
    val description: String = "",

    @Expose
    @SerializedName("article_part")
    val articlePart: String = "",

    @Expose
    @SerializedName("references")
    val references: ArrayList<String> = ArrayList(),

    @Expose
    @SerializedName("keywords")
    val keywords: ArrayList<String> = ArrayList(),

    @Expose
    @SerializedName("tools")
    val tools: ArrayList<Tool> = ArrayList(),

)