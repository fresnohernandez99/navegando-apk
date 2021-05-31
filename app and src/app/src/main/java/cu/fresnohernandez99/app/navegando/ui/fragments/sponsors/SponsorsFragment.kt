package cu.fresnohernandez99.app.navegando.ui.fragments.sponsors

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.databinding.QuestionLayoutBinding
import cu.fresnohernandez99.app.navegando.databinding.SponsorFragmentBinding
import cu.fresnohernandez99.app.navegando.ui.adapters.QuestionOptionsAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener
import cu.fresnohernandez99.app.navegando.ui.adapters.SponsorAdapter
import cu.fresnohernandez99.app.navegando.ui.base.fragment.BaseFragment
import cu.fresnohernandez99.app.navegando.ui.dialogs.InformationDialog

@Suppress("RedundantNullableReturnType")
class SponsorsFragment : BaseFragment(), RecyclerViewClickListener {

    private lateinit var viewModel: SponsorsViewModel
    private lateinit var sponsorAdapter: SponsorAdapter
    private lateinit var adapter: QuestionOptionsAdapter

    //Views
    private lateinit var vSponsorRecycler: RecyclerView
    private lateinit var questionLayoutBinding: QuestionLayoutBinding
    private lateinit var vSponsorsImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SponsorFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SponsorsViewModel::class.java)

        adapter = QuestionOptionsAdapter(this)
        sponsorAdapter = SponsorAdapter(sponsorListener, imageLoader)

        configView(binding)
        setObservers()
    }

    override fun configView(vb: ViewBinding) {
        super.configView(vb)

        (vb as SponsorFragmentBinding).apply {
            vSponsorRecycler = sponsorRecycler
            adapterV = sponsorAdapter
            questionLayoutBinding = questionCard
            questionLayoutBinding.adapterV = adapter
            questionLayoutBinding.question = viewModel.question
            vSponsorsImage = sponsorsImage
        }
    }

    override fun setObservers() {
        super.setObservers()

        sponsorAdapter.setList(viewModel.sponsors)
        adapter.setList(viewModel.question.options)
    }

    override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
        when (id!!) {
            0 -> whatImSeeing()
            1 -> howToBeThere()
            2 -> navController.navigateUp()
        }
    }

    private val sponsorListener = object : RecyclerViewClickListener {
        override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
            val url = extraData as String
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    private fun whatImSeeing() {
        val informationDialog = InformationDialog(requireContext(), layoutInflater)
        informationDialog.buildAndShow(getString(R.string.info_sponsors))
    }

    private fun howToBeThere() {
        val informationDialog = InformationDialog(requireContext(), layoutInflater)
        informationDialog.buildAndShow(getString(R.string.info_be_there))
    }

}