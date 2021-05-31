package cu.fresnohernandez99.app.navegando.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.data.model.entities.Sponsor
import cu.fresnohernandez99.app.navegando.databinding.ItemSponsorBinding
import cu.fresnohernandez99.app.navegando.utils.imageLoader.ImageLoader

class SponsorAdapter(var itemListener: RecyclerViewClickListener, val imageLoader: ImageLoader) :
    RecyclerView.Adapter<SponsorAdapter.InnerViewHolder>() {

    private val items: MutableList<Sponsor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding =
            DataBindingUtil.inflate<ItemSponsorBinding>(
                inflater,
                R.layout.item_sponsor,
                parent,
                false
            )

        return InnerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                itemListener.recyclerViewListClicked(
                    it,
                    adapterPosition,
                    0,
                    items[adapterPosition].url
                )
            }
        }
    }

    fun setList(list: List<Sponsor>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(list)
        notifyItemRangeChanged(previousItemSize, list.size)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.binding.apply {
            sponsor = items[position]
            imageLoader.loadAsset(items[position].img, sponsorImage, true)
            executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    class InnerViewHolder(val binding: ItemSponsorBinding) :
        RecyclerView.ViewHolder(binding.root)

}