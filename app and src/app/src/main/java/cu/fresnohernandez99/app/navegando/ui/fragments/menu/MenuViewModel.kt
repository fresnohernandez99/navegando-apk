package cu.fresnohernandez99.app.navegando.ui.fragments.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import cu.fresnohernandez99.app.navegando.data.json.JsonManager
import cu.fresnohernandez99.app.navegando.data.mocks.SimpleQuestions
import cu.fresnohernandez99.app.navegando.data.model.entities.Article
import cu.fresnohernandez99.app.navegando.data.repository.ApiRepository
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import cu.fresnohernandez99.app.navegando.ui.adapters.datasource.ArticleDataSource
import cu.fresnohernandez99.app.navegando.ui.adapters.datasource.ArticleDataSourceFactory
import cu.fresnohernandez99.app.navegando.ui.base.viewModels.BaseViewModel
import cu.fresnohernandez99.app.navegando.utils.NetworkHelper
import cu.fresnohernandez99.app.navegando.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val networkHelper: NetworkHelper,
    private val jsonManager: JsonManager,
    private val prefsStore: PrefsStore
) : BaseViewModel() {


    val articlePagedList: LiveData<PagedList<Article>>

    private val liveDataSource: LiveData<ArticleDataSource>

    init {
        val itemDataSourceFactory = ArticleDataSourceFactory(jsonManager)
        liveDataSource = itemDataSourceFactory.articleLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ArticleDataSource.PAGE_SIZE)
            .build()

        articlePagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }

    var articleListQuestion = SimpleQuestions.MENU

    //SUGGESTION
    private val _suggestion = MutableStateFlow<Resource<Any>>(Resource.notRequested())
    val suggestion: StateFlow<Resource<Any>> = _suggestion

    fun makeSuggestion(text: String) {
        viewModelScope.launch {
            _suggestion.value = Resource.loading()
            if (networkHelper.isNetworkConnected()) {
                apiRepository.sendSuggestion(text)?.let {
                    _suggestion.value =
                        if (it.isSuccessful) {
                            Resource.success(null)
                        } else Resource.error(it.errorBody().toString(), null)
                }
            } else _suggestion.value = Resource.error("No internet connection", null)
        }
    }
}