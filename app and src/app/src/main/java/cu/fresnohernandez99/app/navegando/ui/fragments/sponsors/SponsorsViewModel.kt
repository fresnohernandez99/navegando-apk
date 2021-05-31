package cu.fresnohernandez99.app.navegando.ui.fragments.sponsors

import cu.fresnohernandez99.app.navegando.data.json.JsonManager
import cu.fresnohernandez99.app.navegando.data.mocks.SimpleQuestions
import cu.fresnohernandez99.app.navegando.data.mocks.Sponsors
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import cu.fresnohernandez99.app.navegando.ui.base.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SponsorsViewModel @Inject constructor(
    private val jsonManager: JsonManager,
    private val prefsStore: PrefsStore
) : BaseViewModel() {

    var sponsors = Sponsors.list

    var question = SimpleQuestions.SPONSORS

}