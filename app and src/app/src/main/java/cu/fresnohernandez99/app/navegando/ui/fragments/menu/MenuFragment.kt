package cu.fresnohernandez99.app.navegando.ui.fragments.menu

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.databinding.MenuFragmentBinding
import cu.fresnohernandez99.app.navegando.databinding.QuestionLayoutBinding
import cu.fresnohernandez99.app.navegando.ui.activities.main.MainActivity
import cu.fresnohernandez99.app.navegando.ui.adapters.ArticlesMiniPageAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.QuestionOptionsAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener
import cu.fresnohernandez99.app.navegando.ui.base.fragment.BaseFragment
import cu.fresnohernandez99.app.navegando.ui.dialogs.DialogClickListener
import cu.fresnohernandez99.app.navegando.ui.dialogs.FillContentDialog
import cu.fresnohernandez99.app.navegando.ui.dialogs.InformationDialog
import cu.fresnohernandez99.app.navegando.utils.ArgConstants
import cu.fresnohernandez99.app.navegando.utils.enums.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("RedundantNullableReturnType")
class MenuFragment : BaseFragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var articlesMiniPageAdapter: ArticlesMiniPageAdapter
    private lateinit var adapter: QuestionOptionsAdapter

    //Views
    private lateinit var questionLayoutBinding: QuestionLayoutBinding
    private lateinit var vListImage: ImageView
    private lateinit var vArticleRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        articlesMiniPageAdapter = ArticlesMiniPageAdapter(articleMiniListener)

        adapter = QuestionOptionsAdapter(optionsListener)

        configView(binding)
        setObservers()
    }

    override fun configView(vb: ViewBinding) {
        super.configView(vb)

        (vb as MenuFragmentBinding).apply {
            vm = viewModel
            questionLayoutBinding = questionCard
            articlesAdapter = articlesMiniPageAdapter
            questionLayoutBinding.adapterV = adapter
            vListImage = listImage
            vArticleRecycler = articleRecycler
        }
    }

    override fun setObservers() {
        super.setObservers()

        lifecycleScope.launchWhenCreated {
            requireActivity().runOnUiThread {
                questionLayoutBinding.question = viewModel.articleListQuestion
                adapter.setList(viewModel.articleListQuestion.options)
            }

            //send suggestion
            launch {
                val progressDialog = ProgressDialog(requireContext())
                viewModel.suggestion.collect { resource ->
                    when (resource.status) {
                        Status.NOT_REQUESTED -> {
                        }
                        Status.LOADING -> {
                            progressDialog.setMessage("Enviando...")
                            progressDialog.show()
                        }
                        Status.ERROR -> {
                            progressDialog.dismiss()
                            Toast.makeText(requireContext(), "Ha ocurrido un error al enviar la sugerencia. Debe esperar un día antes de enviar otra.", Toast.LENGTH_LONG).show()
                        }
                        Status.SUCCESS -> {
                            progressDialog.dismiss()
                            Toast.makeText(requireContext(), "Su sugerencia ha sido enviada. Debe esperar un día antes de enviar otra.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        viewModel.articlePagedList.observe(viewLifecycleOwner, {
            articlesMiniPageAdapter.submitList(it)
            MainActivity.quitLoading()
        })
    }

    private var articleMiniListener = object : RecyclerViewClickListener {
        override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
            MainActivity.setLoading()
            navController.navigate(
                R.id.action_menuFragment_to_tutorialsFragment,
                bundleOf(
                    "mode" to ArgConstants.MODE_REVIEW,
                    "numValue" to id.toString()
                )
            )
        }
    }

    private var optionsListener = object : RecyclerViewClickListener {
        override fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?) {
            when (id!!) {
                0 -> whatImSeeing()
                1 -> sendSuggestion()
                2 -> navController.navigateUp()
            }
        }
    }

    private fun whatImSeeing() {
        val informationDialog = InformationDialog(requireContext(), layoutInflater)
        informationDialog.buildAndShow(getString(R.string.info_menu))
    }

    private fun sendSuggestion() {
        val dialogClickListener = object : DialogClickListener {
            override fun okClicked(view: View?, data: Any?) {
                val text = data as String
                viewModel.makeSuggestion(text)
            }
        }
        val fillContentDialog =
            FillContentDialog(requireContext(), layoutInflater, dialogClickListener)
        fillContentDialog.buildAndShow(getString(R.string.send_suggestion))
    }
}