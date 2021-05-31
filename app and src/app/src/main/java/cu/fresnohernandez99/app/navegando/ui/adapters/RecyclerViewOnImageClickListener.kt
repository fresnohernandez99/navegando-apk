package cu.fresnohernandez99.app.navegando.ui.adapters

import android.view.View

interface RecyclerViewOnImageClickListener {
    fun recyclerViewImageClicked(V: View, imageName: String, imageType: String)
}