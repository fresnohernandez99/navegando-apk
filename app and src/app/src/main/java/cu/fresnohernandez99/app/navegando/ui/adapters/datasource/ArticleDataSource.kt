package cu.fresnohernandez99.app.navegando.ui.adapters.datasource

import androidx.paging.PageKeyedDataSource
import cu.fresnohernandez99.app.navegando.data.json.JsonManager
import cu.fresnohernandez99.app.navegando.data.model.entities.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleDataSource(private val jsonManager: JsonManager) :
    PageKeyedDataSource<Int, Article>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            jsonManager.getMenuArticles(FIRS_PAGE).let {
                callback.onResult(it, null, FIRS_PAGE)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        val key = params.key + 6
        GlobalScope.launch(Dispatchers.IO) {
            jsonManager.getMenuArticles(key).let {
                callback.onResult(it, key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        var key = params.key
        GlobalScope.launch(Dispatchers.IO) {
            jsonManager.getMenuArticles(key).let {
                if (key > 6) key -= 6 else key = 0
                callback.onResult(it, key)
            }
        }
    }


    companion object {
        const val PAGE_SIZE = 6
        const val FIRS_PAGE = 1
    }
}