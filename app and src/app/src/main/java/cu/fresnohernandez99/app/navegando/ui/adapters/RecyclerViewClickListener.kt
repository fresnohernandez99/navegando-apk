package cu.fresnohernandez99.app.navegando.ui.adapters

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(V: View, pos: Int, id: Int?, extraData: Any?)
}