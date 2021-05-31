package cu.fresnohernandez99.app.navegando.ui.adapters.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import cu.fresnohernandez99.app.navegando.data.json.JsonManager
import cu.fresnohernandez99.app.navegando.data.model.entities.Article

class ArticleDataSourceFactory(private val jsonManager: JsonManager) :
    DataSource.Factory<Int, Article>() {

    val articleLiveDataSource = MutableLiveData<ArticleDataSource>()

    override fun create(): DataSource<Int, Article> {
        val articleDataSource = ArticleDataSource(jsonManager)
        articleLiveDataSource.postValue(articleDataSource)
        return articleDataSource
    }
}