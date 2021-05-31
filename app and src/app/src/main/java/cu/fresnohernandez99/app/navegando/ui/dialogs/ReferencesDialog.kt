package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import cu.fresnohernandez99.app.navegando.databinding.ReferencesLayoutBinding

class ReferencesDialog(
    val context: Context,
    val inflater: LayoutInflater
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val referencesLayoutBinding = ReferencesLayoutBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    fun build(references: ArrayList<String>) {
        dialogBuilder.setView(referencesLayoutBinding.root)
        referencesLayoutBinding.apply {
            reference1 = references[0]

            goReference1.setOnClickListener {
                val url = references[0]
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }

            if (references.size > 1) {
                reference2 = references[1]
                goReference2.setOnClickListener {
                    val url = references[1]
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }

            } else goReference2.visibility = View.GONE

            readyBtn.setOnClickListener {
                dismiss()
            }
        }
        ins = dialogBuilder.create()
        ins.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun buildAndShow(r: ArrayList<String>) {
        build(r)
        show()
    }

    fun show() = ins.show()

    fun dismiss() = ins.dismiss()

}