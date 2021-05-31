package cu.fresnohernandez99.app.navegando.ui.activities.main

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import cu.fresnohernandez99.app.navegando.data.model.entities.Session
import cu.fresnohernandez99.app.navegando.data.model.entities.Update
import cu.fresnohernandez99.app.navegando.data.repository.ApiRepository
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import cu.fresnohernandez99.app.navegando.ui.base.viewModels.BaseViewModel
import cu.fresnohernandez99.app.navegando.utils.NetworkHelper
import cu.fresnohernandez99.app.navegando.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val networkHelper: NetworkHelper,
    private val prefsStore: PrefsStore
) : BaseViewModel() {

    fun username() = prefsStore.username()

    val viewProgressBar: ObservableInt = ObservableInt(View.GONE)

    private val _update = MutableStateFlow<Resource<Update>>(Resource.notRequested())
    val update: StateFlow<Resource<Update>> = _update

    init {
        //checkForUpdates()
    }

    fun checkForUpdates() {
        viewModelScope.launch {
            _update.value = Resource.loading()
            if (networkHelper.isNetworkConnected()) {
                apiRepository.getUpdates()?.let {
                    _update.value =
                        if (it.isSuccessful) {
                            Resource.success(it.body())
                        } else Resource.error(it.errorBody().toString(), null)
                }
            } else _update.value = Resource.error("No internet connection", null)
        }
    }

    //LOGIN
    suspend fun saveUserId(ui: String) = prefsStore.setSessionUserId(ui)

    private val _login = MutableStateFlow<Resource<Session>>(Resource.notRequested())
    val login: StateFlow<Resource<Session>> = _login

    fun makeLogin(identifier: String) {
        viewModelScope.launch {
            _login.value = Resource.loading()
            if (networkHelper.isNetworkConnected()) {
                apiRepository.login(identifier)?.let {
                    _login.value =
                        if (it.isSuccessful && it.body() != null) {
                            Resource.success(it.body()!!.box)
                        } else Resource.error(it.errorBody().toString(), null)
                }
            } else _login.value = Resource.error("No internet connection", null)
        }
    }
}