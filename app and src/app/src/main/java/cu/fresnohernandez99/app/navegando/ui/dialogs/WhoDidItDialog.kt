package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import cu.fresnohernandez99.app.navegando.databinding.InformationLayoutBinding
import cu.fresnohernandez99.app.navegando.databinding.WhoDidItLayoutBinding
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener

class WhoDidItDialog(
    val context: Context,
    val inflater: LayoutInflater
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val whoDidItLayoutBinding = WhoDidItLayoutBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    fun build() {
        dialogBuilder.setView(whoDidItLayoutBinding.root)
        whoDidItLayoutBinding.apply {

            goTwitter.setOnClickListener {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.twitter.com/GOku99PRO")
                    )
                )
            }

            goLinkedIn.setOnClickListener {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/")
                    )
                )
            }

            readyBtn.setOnClickListener {
                dismiss()
            }
        }
        ins = dialogBuilder.create()
        ins.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun buildAndShow() {
        build()
        show()
    }

    fun show() = ins.show()

    fun dismiss() = ins.dismiss()

}