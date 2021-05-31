package cu.fresnohernandez99.app.navegando.ui.fragments.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.databinding.HelpFragmentBinding
import cu.fresnohernandez99.app.navegando.databinding.QuestionHelpLayoutBinding
import cu.fresnohernandez99.app.navegando.databinding.QuestionLayoutBinding
import cu.fresnohernandez99.app.navegando.ui.adapters.QuestionOptionsAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener
import cu.fresnohernandez99.app.navegando.ui.base.fragment.BaseFragment
import cu.fresnohernandez99.app.navegando.ui.dialogs.CanIHelpDialog
import cu.fresnohernandez99.app.navegando.ui.dialogs.InformationDialog
import cu.fresnohernandez99.app.navegando.ui.dialogs.WhoDidItDialog
import cu.fresnohernandez99.app.navegando.utils.DateTimeUtils
import java.util.*

@Suppress("RedundantNullableReturnType")
class HelpFragment : BaseFragment(), RecyclerViewClickListener {

    private lateinit var viewModel: HelpViewModel
    private lateinit var adapter: QuestionOptionsAdapter

    //Views
    private lateinit var questionLayoutBinding: QuestionHelpLayoutBinding
    private lateinit var vBottomText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HelpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)

        adapter = QuestionOptionsAdapter(this)

        configView(binding)
        setObservers()
    }

    override fun configView(vb: ViewBinding) {
        super.configView(vb)

        (vb as HelpFragmentBinding).apply {
            questionLayoutBinding = questionCard
            questionCard.adapterV = adapter
            questionCard.question = viewModel.helpQuestion
            vBottomText = bottomText
        }

        val version = activity?.applicationContext?.packageManager?.getPackageInfo(
            activity?.packageName!!,
            0
        )?.versionName

        ("Navegando $version ${DateTimeUtils.getYear(Date())}").also { vBottomText.text = it }
    }


    override fun setObservers() {
        super.setObservers()

        adapter.setList(viewModel.helpQuestion.options)
    }

    override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
        when (id!!) {
            0 -> whatsAbout()
            1 -> whoDitIt()
            2 -> canIHelp()
        }
    }

    private fun whatsAbout(){
        val informationDialog = InformationDialog(requireContext(), layoutInflater)
        informationDialog.buildAndShow(getString(R.string.whats_About))
    }

    private fun whoDitIt(){
        val whoDidItDialog = WhoDidItDialog(requireContext(), layoutInflater)
        whoDidItDialog.buildAndShow()
    }

    private fun canIHelp(){
        val canIHelpDialog = CanIHelpDialog(requireContext(), layoutInflater, requireActivity())
        canIHelpDialog.buildAndShow()
    }

}