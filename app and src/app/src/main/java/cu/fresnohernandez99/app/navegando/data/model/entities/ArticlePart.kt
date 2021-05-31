package cu.fresnohernandez99.app.navegando.data.model.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import cu.fresnohernandez99.app.navegando.utils.ArgConstants

data class ArticlePart(
    //Image Name Format: article_title + article_id + _ + image_number

    @Expose
    @SerializedName("id")
    var id: Int = 0,

    @Expose
    @SerializedName("title")
    val title: String = "",

    @Expose
    @SerializedName("subtitle")
    val subtitle: String? = null,

    @Expose
    @SerializedName("text")
    val text: String = "",

    @Expose
    @SerializedName("image_type")
    val imageType: String = ArgConstants.BITMAP,

    @Expose
    @SerializedName("img_url")
    val imgUrl: String = ""

)