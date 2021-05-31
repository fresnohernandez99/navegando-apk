package cu.fresnohernandez99.app.navegando.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.data.model.entities.ArticlePart
import cu.fresnohernandez99.app.navegando.databinding.ItemArticleBinding
import cu.fresnohernandez99.app.navegando.utils.ArgConstants
import cu.fresnohernandez99.app.navegando.utils.imageLoader.ImageLoader

class ArticleAdapter(
    val imageLoader: ImageLoader,
    var imageTouchListener: RecyclerViewOnImageClickListener
) :
    RecyclerView.Adapter<ArticleAdapter.InnerViewHolder>() {

    private val items: MutableList<ArticlePart> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding =
            DataBindingUtil.inflate<ItemArticleBinding>(
                inflater,
                R.layout.item_article,
                parent,
                false
            )

        return InnerViewHolder(binding).apply {
            binding.root.setOnClickListener {
            }
        }
    }

    fun setList(list: List<ArticlePart>) {
        items.clear()
        notifyDataSetChanged()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.binding.apply {
            val actualItem = items[position]
            title = actualItem.title
            subtitle = actualItem.subtitle
            text = actualItem.text

            when (actualItem.imageType) {
                ArgConstants.BITMAP -> {
                    imageLoader.loadAssetForTutorial(
                        actualItem.imgUrl,
                        imgArticle,
                        false
                    )
                    imgArticle.setOnClickListener {
                        imageTouchListener.recyclerViewImageClicked(
                            it,
                            actualItem.imgUrl,
                            actualItem.imageType
                        )
                    }
                }
                ArgConstants.VECTOR -> {
                    imageLoader.loadDrawable(
                        actualItem.imgUrl,
                        imgArticle,
                        false
                    )
                    imgArticle.setOnClickListener {}
                }
            }
            executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    class InnerViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
}