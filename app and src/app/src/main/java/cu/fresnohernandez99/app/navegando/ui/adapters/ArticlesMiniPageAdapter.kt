package cu.fresnohernandez99.app.navegando.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.data.model.entities.Article
import cu.fresnohernandez99.app.navegando.databinding.ItemArticleMiniBinding

class ArticlesMiniPageAdapter(var itemListener: RecyclerViewClickListener) :
    PagedListAdapter<Article, ArticlesMiniPageAdapter.InnerViewHolder>(ARTICLE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding =
            DataBindingUtil.inflate<ItemArticleMiniBinding>(
                inflater,
                R.layout.item_article_mini,
                parent,
                false
            )

        return InnerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                itemListener.recyclerViewListClicked(
                    it,
                    adapterPosition,
                    getItem(adapterPosition)?.id,
                    null
                )
            }
        }
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.binding.apply {
            item = getItem(position)
            executePendingBindings()
        }
    }

    class InnerViewHolder(val binding: ItemArticleMiniBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article)=
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article) =
                newItem == oldItem
        }
    }
}