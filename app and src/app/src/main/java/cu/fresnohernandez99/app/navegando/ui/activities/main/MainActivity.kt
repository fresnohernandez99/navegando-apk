package cu.fresnohernandez99.app.navegando.ui.activities.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Secure
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.databinding.ActivityMainBinding
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStoreImpl
import cu.fresnohernandez99.app.navegando.ui.base.activity.BaseActivity
import cu.fresnohernandez99.app.navegando.utils.enums.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Navegando)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configView(binding)
        setListeners()
        setObservers()
    }

    override fun configView(vb: ViewBinding) {
        super.configView(vb)
        vb.root.background = ContextCompat.getDrawable(this, R.drawable.ic_background_white)

        (vb as ActivityMainBinding).apply {
            vm = mainViewModel
        }
        initialize(mainViewModel)
    }

    @SuppressLint("HardwareIds")
    override fun setObservers() {
        super.setObservers()
        lifecycleScope.launchWhenStarted {
            //wait for it have a name
            launch {
                mainViewModel.username().collect { username ->
                    if (username != PrefsStoreImpl.DEFAULT_NULL_STRING) {
                        val androidId = Secure.getString(
                            contentResolver,
                            Secure.ANDROID_ID
                        )
                        mainViewModel.makeLogin(username + androidId)
                    }
                }
            }

            //send login request
            launch {
                mainViewModel.login.collect { resource ->
                    if (resource.status == Status.SUCCESS) {
                        resource.data?.userId?.let { userId ->
                            mainViewModel.saveUserId(userId)
                        }
                    }
                }
            }

            //waiting for update response
            launch {
                mainViewModel.update.collect {
                    if (it.status == Status.SUCCESS) {
                        //TODO show there is an update
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        quitLoading()
    }

    companion object {
        private lateinit var vm: MainViewModel

        fun initialize(v: MainViewModel) {
            vm = v
        }

        fun setLoading() = vm.viewProgressBar.set(View.VISIBLE)

        fun quitLoading() {
            GlobalScope.launch(Dispatchers.IO) {
                delay(200)
                vm.viewProgressBar.set(View.GONE)
            }
        }

    }
}