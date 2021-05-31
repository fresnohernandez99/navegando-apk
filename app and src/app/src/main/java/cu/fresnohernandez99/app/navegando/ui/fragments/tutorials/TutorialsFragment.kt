package cu.fresnohernandez99.app.navegando.ui.fragments.tutorials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.data.json.JsonManager
import cu.fresnohernandez99.app.navegando.data.model.entities.Vote
import cu.fresnohernandez99.app.navegando.databinding.QuestionLayoutBinding
import cu.fresnohernandez99.app.navegando.databinding.ToolsLayout2Binding
import cu.fresnohernandez99.app.navegando.databinding.TutorialsFragmentBinding
import cu.fresnohernandez99.app.navegando.ui.activities.main.MainActivity
import cu.fresnohernandez99.app.navegando.ui.adapters.ArticleAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.QuestionOptionsAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewOnImageClickListener
import cu.fresnohernandez99.app.navegando.ui.base.fragment.BaseFragment
import cu.fresnohernandez99.app.navegando.ui.dialogs.*
import cu.fresnohernandez99.app.navegando.utils.ArgConstants
import cu.fresnohernandez99.app.navegando.utils.MyPermissions.isCallPermissionGranted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Suppress("RedundantNullableReturnType")
class TutorialsFragment : BaseFragment(), RecyclerViewClickListener,
    RecyclerViewOnImageClickListener {

    private lateinit var viewModel: TutorialsViewModel
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var adapter: QuestionOptionsAdapter
    private var mode = ArgConstants.MODE_TUTORIAL
    private var thisState = 1
    private var lastPos = 0

    //Views
    private lateinit var vArticleRecycler: RecyclerView
    private lateinit var questionLayoutBinding: QuestionLayoutBinding
    private lateinit var toolsLayoutBinding: ToolsLayout2Binding
    private lateinit var vScroll: NestedScrollView
    private lateinit var showReferencesBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TutorialsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TutorialsViewModel::class.java)

        mode = requireArguments().getString("mode", ArgConstants.MODE_TUTORIAL)

        articleAdapter = ArticleAdapter(imageLoader, this)
        adapter = QuestionOptionsAdapter(this)

        configView(binding)
        setObservers()
        isCallPermissionGranted(requireActivity())
    }

    override fun configView(vb: ViewBinding) {
        super.configView(vb)

        (vb as TutorialsFragmentBinding).apply {
            vm = viewModel
            adapterV = adapter
            articleAdapterV = articleAdapter
            vArticleRecycler = articleRecycler
            questionLayoutBinding = questionCard
            questionLayoutBinding.adapterV = adapter
            questionLayoutBinding.question = viewModel.question
            toolsLayoutBinding = toolsCard
            toolsLayoutBinding.visibility = View.GONE
            vScroll = scroll
            showReferencesBtn = showReference
        }
        adapter.setList(viewModel.question.options)
    }

    override fun setObservers() {
        super.setObservers()

        lifecycleScope.launch {
            when (mode) {
                ArgConstants.MODE_TUTORIAL -> {
                    launch {
                        viewModel.lastPosition().collect { lP ->
                            lastPos = lP
                        }
                    }

                    launch {
                        viewModel.state().collect { state ->
                            thisState = state

                            if (thisState == 1) firstTutorial()
                            findAndSetData(thisState)
                        }
                    }

                }
                ArgConstants.MODE_REVIEW -> {
                    findAndSetData(requireArguments().getString("numValue", "1").toInt())
                }
            }
        }
    }

    private suspend fun findAndSetData(numValue: Int) {
        viewModel.setNeedItArticle(numValue)

        (binding as TutorialsFragmentBinding).apply {
            articleTitle = viewModel.article.title
        }

        if (viewModel.article.tools.size > 0) {
            toolsLayoutBinding.visibility = View.VISIBLE
            setTools()
        } else {
            toolsLayoutBinding.visibility = View.GONE
        }

        if (viewModel.article.references.size > 0) {
            showReferencesBtn.visibility = View.VISIBLE
            showReferencesBtn.setOnClickListener { showReferences() }
        } else {
            showReferencesBtn.visibility = View.GONE
            showReferencesBtn.setOnClickListener { }
        }

        requireActivity().runOnUiThread {
            articleAdapter.setList(viewModel.articleParts.articleParts)
        }
        delay(300)
        vScroll.scrollTo(0, lastPos)

        MainActivity.quitLoading()
    }

    override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
        when (id!!) {
            0 -> markArticleAs(ArgConstants.GOOD_ARTICLE)
            1 -> markArticleAs(ArgConstants.BAD_ARTICLE)
            2 -> goBack()
        }
    }

    private fun markArticleAs(how: String) {
        val newVote = Vote()
        newVote.articleId = viewModel.article.id
        when (how) {
            ArgConstants.GOOD_ARTICLE -> {
                newVote.vote = ArgConstants.GOOD_ARTICLE
                lifecycleScope.launch {
                    viewModel.saveVote(newVote)
                }
                continueProgress()
            }
            ArgConstants.BAD_ARTICLE -> {
                newVote.vote = ArgConstants.BAD_ARTICLE
                if (mode == ArgConstants.MODE_TUTORIAL) askAReason(newVote)
                else continueProgress()
            }
        }

        lifecycleScope.launch {
            if (mode == ArgConstants.MODE_TUTORIAL) {
                lastPos = 0
                viewModel.resetPosition()
            }
        }
    }

    private fun continueProgress() {
        when (mode) {
            ArgConstants.MODE_TUTORIAL -> {
                if (JsonManager.articleCount >= (thisState + 1))
                    lifecycleScope.launch {
                        MainActivity.setLoading()
                        viewModel.increaseState()
                    }
                else {
                    markAsEnd()
                }
            }
            ArgConstants.MODE_REVIEW -> {
                navController.navigateUp()
            }
        }
    }

    private fun askAReason(newVote: Vote) {
        val dialogClickListener = object : DialogClickListener {
            override fun okClicked(view: View?, data: Any?) {
                val explication = data as String
                newVote.explication = explication
                lifecycleScope.launch {
                    viewModel.saveVote(newVote)
                }
                continueProgress()
            }
        }
        val fillContentDialog =
            FillContentDialog(requireContext(), layoutInflater, dialogClickListener)
        fillContentDialog.buildAndShow(getString(R.string.send_reason))
    }

    private fun markAsEnd() {
        navController.navigateUp()
        val informationDialog = InformationDialog(requireContext(), layoutInflater)
        informationDialog.buildAndShow(getString(R.string.end_for_now))
    }

    private fun resetStatus() {
        GlobalScope.launch {
            viewModel.resetState()
        }
    }

    private fun showReferences() {
        val referencesDialog = ReferencesDialog(requireContext(), layoutInflater)
        referencesDialog.buildAndShow(viewModel.article.references)
    }

    private fun goBack() {
        navController.navigateUp()
    }

    private fun firstTutorial() {
        val informationDialog = InformationDialog(requireContext(), layoutInflater)
        informationDialog.buildAndShow(getString(R.string.fresh_start_init))
    }

    override fun recyclerViewImageClicked(V: View, imageName: String, imageType: String) {
        val imagePreviewDialog = ImagePreviewDialog(requireContext(), layoutInflater, imageLoader)
        imagePreviewDialog.buildAndShow(imageName, imageType)
    }

    private fun setTools() {
        viewModel.article.tools.forEach { tool ->
            //Tool Link
            if (tool.type == ArgConstants.TOOL_LINK) {
                toolsLayoutBinding.toolLink.let {
                    it.visibility = View.VISIBLE
                    it.setOnClickListener {
                        val url = tool.data
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }
                }
            } else {
                toolsLayoutBinding.toolLink.let {
                    it.visibility = View.GONE
                    it.setOnClickListener { }
                }
            }

            //Tool Call
            if (tool.type == ArgConstants.TOOL_CALL_USD) {
                toolsLayoutBinding.toolCall.let {
                    it.visibility = View.VISIBLE
                    it.setOnClickListener {
                        val num = tool.data
                        if (isCallPermissionGranted(requireActivity()))
                            try {
                                val intent = Intent(Intent.ACTION_CALL)
                                intent.data =
                                    Uri.parse("tel:$num" + Uri.encode("#"))
                                startActivity(intent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                    }
                }
            } else {
                toolsLayoutBinding.toolCall.let {
                    it.visibility = View.GONE
                    it.setOnClickListener { }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mode == ArgConstants.MODE_TUTORIAL) {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.saveLastPosition(vScroll.scrollY)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (mode == ArgConstants.MODE_TUTORIAL) {
            GlobalScope.launch(Dispatchers.IO) {
                val votes = viewModel.getVotes()
                viewModel.sendUpdateRecord(votes)
            }
        }
    }

}