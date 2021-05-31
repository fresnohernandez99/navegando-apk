package cu.fresnohernandez99.app.navegando.ui.base.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import cu.fresnohernandez99.app.navegando.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    var _binding: ViewBinding? = null
    val binding get() = _binding!!
    lateinit var navController: NavController

    open fun configView(vb: ViewBinding) {
        navController = findNavController(R.id.nav_host_fragment)
    }
    open fun setListeners() {}
    open fun setObservers() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}