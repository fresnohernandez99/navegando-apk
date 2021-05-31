package cu.fresnohernandez99.app.navegando.ui.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.data.mocks.SimpleQuestions
import cu.fresnohernandez99.app.navegando.databinding.QuestionLayoutBinding
import cu.fresnohernandez99.app.navegando.databinding.StartFragmentBinding
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStoreImpl
import cu.fresnohernandez99.app.navegando.ui.activities.main.MainActivity
import cu.fresnohernandez99.app.navegando.ui.adapters.QuestionOptionsAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener
import cu.fresnohernandez99.app.navegando.ui.base.fragment.BaseFragment
import cu.fresnohernandez99.app.navegando.ui.dialogs.DialogClickListener
import cu.fresnohernandez99.app.navegando.ui.dialogs.FillContentSingleLineDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StartFragment : BaseFragment(), RecyclerViewClickListener {

    private lateinit var viewModel: StartViewModel
    private lateinit var adapter: QuestionOptionsAdapter
    private var firstTime = true
    private var setName = false

    //views
    private lateinit var questionLayoutBinding: QuestionLayoutBinding
    private lateinit var vMotionLayout: MotionLayout
    private lateinit var vStartImage: ImageView
    private lateinit var vConfigsBtn: ImageButton
    private lateinit var vUsername: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StartViewModel::class.java)

        MainActivity.quitLoading()
        adapter = QuestionOptionsAdapter(this)

        configView(binding)
        setObservers()
    }

    override fun configView(vb: ViewBinding) {
        super.configView(vb)
        (vb as StartFragmentBinding).apply {
            vm = viewModel
            questionLayoutBinding = questionCard
            questionLayoutBinding.adapterV = adapter
            vMotionLayout = motionLayout
            vStartImage = startImage
            vConfigsBtn = configsBtn
            vUsername = username
        }

        vStartImage.layoutParams.width = sizeHelper.xdpi.toInt() * 2

        vConfigsBtn.setOnClickListener {
        }
    }

    override fun setObservers() {
        super.setObservers()

        prepareMenu()
        checkFirstTime()
    }

    override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
        var goTo = 0
        when (id!!) {
            0 -> {
                MainActivity.setLoading()
                goTo = R.id.action_startFragment_to_tutorialsFragment
            }
            1 -> {
                MainActivity.setLoading()
                goTo = R.id.action_startFragment_to_menuFragment
            }
            3 -> goTo = R.id.action_startFragment_to_sponsorsFragment
            4 -> goTo = R.id.action_startFragment_to_helpFragment
        }

        lifecycleScope.launch {
            viewModel.setFalseFirstTime()
        }
        if (goTo != 0) navController.navigate(goTo)
    }

    private fun checkFirstTime() {
        lifecycleScope.launch {
            viewModel.username = viewModel.getUsername().first()
            if (viewModel.username != PrefsStoreImpl.DEFAULT_NULL_STRING) {
                setName = true
                vUsername.text = viewModel.username
                vMotionLayout.transitionToState(R.id.end)
            } else askForUsername()
        }
    }

    private fun askForUsername() {
        val dialogClickListener = object : DialogClickListener {
            override fun okClicked(view: View?, data: Any?) {
                val username = data as String
                saveUsername(username)
            }
        }
        val fillContentSingleLineDialog =
            FillContentSingleLineDialog(requireContext(), layoutInflater, dialogClickListener)
        fillContentSingleLineDialog.buildAndShow(getString(R.string.t_tell_me_your_name), false)
    }

    private fun saveUsername(username: String) {
        lifecycleScope.launch { viewModel.setUsername(username) }
        setName = true
        vMotionLayout.transitionToState(R.id.end)
        hideKeyboard()
    }

    private fun prepareMenu() {
        lifecycleScope.launch {
            firstTime = viewModel.firsTime().first()
            requireActivity().runOnUiThread {
                viewModel.firstQuestion =
                    if (firstTime) SimpleQuestions.FIRST_1 else SimpleQuestions.FIRST_2
                adapter.setList(viewModel.firstQuestion.options)
                questionLayoutBinding.question = viewModel.firstQuestion
            }
        }
    }

}