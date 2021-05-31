package cu.fresnohernandez99.app.navegando.ui.fragments.tutorials

import cu.fresnohernandez99.app.navegando.data.json.JsonManager
import cu.fresnohernandez99.app.navegando.data.mocks.SimpleQuestions
import cu.fresnohernandez99.app.navegando.data.model.entities.Article
import cu.fresnohernandez99.app.navegando.data.model.entities.ArticleParts
import cu.fresnohernandez99.app.navegando.data.model.entities.Vote
import cu.fresnohernandez99.app.navegando.data.repository.ApiRepository
import cu.fresnohernandez99.app.navegando.data.repository.RoomRepository
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import cu.fresnohernandez99.app.navegando.ui.base.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialsViewModel @Inject constructor(
    private val jsonManager: JsonManager,
    private val prefsStore: PrefsStore,
    private val roomRepository: RoomRepository,
    private val apiRepository: ApiRepository
) : BaseViewModel() {

    fun state() = prefsStore.lastState()

    suspend fun increaseState() = prefsStore.increaseLastState()

    suspend fun resetState() = prefsStore.resetLastState()

    lateinit var article: Article
    lateinit var articleParts: ArticleParts
    var question = SimpleQuestions.SURVEY

    suspend fun setNeedItArticle(s: Int) {
        article = jsonManager.getArticle("${JsonManager.STRUCTURE_ARTICLE}${s}")
        articleParts = jsonManager.getArticleParts(article.articlePart)
    }

    suspend fun saveVote(vote: Vote) = roomRepository.insertVote(vote)
    suspend fun getVotes() = roomRepository.getVotes()

    suspend fun sendUpdateRecord(v: List<Vote>) = apiRepository.updateRecord(v)

    fun lastPosition() = prefsStore.lastPosition()

    suspend fun resetPosition() = prefsStore.resetLastPosition()

    suspend fun saveLastPosition(actualPos: Int) = prefsStore.setLastPosition(actualPos)
}