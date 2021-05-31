package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import cu.fresnohernandez99.app.navegando.databinding.FillContentSingleLineLayoutBinding

class FillContentSingleLineDialog(
    val context: Context,
    val inflater: LayoutInflater,
    val dialogClickListener: DialogClickListener
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val fillContentSingleLineLayoutBinding =
        FillContentSingleLineLayoutBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    fun build(t: String, cancelable: Boolean) {
        dialogBuilder.setView(fillContentSingleLineLayoutBinding.root)
        dialogBuilder.setCancelable(cancelable)
        fillContentSingleLineLayoutBinding.apply {
            text = t

            acceptBtn.setOnClickListener {
                if (inputContent.editText!!.text.isNullOrEmpty()) {
                    inputContent.error = "No puede estar vacío"
                } else {
                    inputContent.isErrorEnabled = false
                    dialogClickListener.okClicked(it, inputContent.editText!!.text.toString())
                    dismiss()
                }
            }
        }
        ins = dialogBuilder.create()
        ins.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun buildAndShow(t: String, c: Boolean) {
        build(t, c)
        show()
    }

    fun show() = ins.show()

    fun dismiss() = ins.dismiss()

}