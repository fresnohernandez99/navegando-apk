package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import cu.fresnohernandez99.app.navegando.databinding.InformationLayoutBinding
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener

class InformationDialog(
    val context: Context,
    val inflater: LayoutInflater
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val informationLayoutBinding = InformationLayoutBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    fun build(t: String) {
        dialogBuilder.setView(informationLayoutBinding.root)
        informationLayoutBinding.apply {
            text = t

            readyBtn.setOnClickListener {
                dismiss()
            }
        }
        ins = dialogBuilder.create()
        ins.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun buildAndShow(t: String) {
        build(t)
        show()
    }

    fun show() = ins.show()

    fun dismiss() = ins.dismiss()

}