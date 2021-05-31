package cu.fresnohernandez99.app.navegando.ui.fragments.start

import cu.fresnohernandez99.app.navegando.data.json.JsonManager
import cu.fresnohernandez99.app.navegando.data.mocks.SimpleQuestions
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import cu.fresnohernandez99.app.navegando.ui.base.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val jsonManager: JsonManager,
    private val prefsStore: PrefsStore
) : BaseViewModel() {

    var username = ""

    fun firsTime() = prefsStore.firstTime()
    fun getUsername() = prefsStore.username()

    var firstQuestion = SimpleQuestions.FIRST_2

    suspend fun setFalseFirstTime() = prefsStore.setFalseFirstTime()

    suspend fun setUsername(username: String) = prefsStore.setUsername(username)

}