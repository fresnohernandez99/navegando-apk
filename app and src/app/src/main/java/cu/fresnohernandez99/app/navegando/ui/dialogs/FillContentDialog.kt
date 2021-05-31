package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import cu.fresnohernandez99.app.navegando.databinding.FillContentLayoutBinding

class FillContentDialog(
    val context: Context,
    val inflater: LayoutInflater,
    val dialogClickListener: DialogClickListener
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val fillContentLayoutBinding = FillContentLayoutBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    fun build(t: String) {
        dialogBuilder.setView(fillContentLayoutBinding.root)
        fillContentLayoutBinding.apply {
            text = t

            readyBtn.setOnClickListener {
                dialogClickListener.okClicked(it, inputContent.editText!!.text.toString())
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