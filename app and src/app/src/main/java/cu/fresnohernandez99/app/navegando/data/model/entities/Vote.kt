package cu.fresnohernandez99.app.navegando.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "vote")
class Vote {
    @PrimaryKey
    @Expose
    @SerializedName("articleId")
    @ColumnInfo(name = "articleId")
    var articleId: Int = 0

    @Expose
    @SerializedName("vote")
    @ColumnInfo(name = "vote")
    var vote: String = ""

    @Expose
    @SerializedName("explication")
    @ColumnInfo(name = "explication")
    var explication: String = ""
}