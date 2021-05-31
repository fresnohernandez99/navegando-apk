package cu.fresnohernandez99.app.navegando.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.data.model.entities.Option
import cu.fresnohernandez99.app.navegando.databinding.ItemOptionBinding

class QuestionOptionsAdapter(var itemListener: RecyclerViewClickListener) :
    RecyclerView.Adapter<QuestionOptionsAdapter.InnerViewHolder>() {

    private val items: MutableList<Option> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding =
            DataBindingUtil.inflate<ItemOptionBinding>(
                inflater,
                R.layout.item_option,
                parent,
                false
            )

        return InnerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                itemListener.recyclerViewListClicked(
                    it,
                    adapterPosition,
                    items[adapterPosition].id,
                    null
                )
            }
        }
    }

    fun setList(list: List<Option>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.binding.apply {
            item = items[position]
            executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    class InnerViewHolder(val binding: ItemOptionBinding) :
        RecyclerView.ViewHolder(binding.root)
}