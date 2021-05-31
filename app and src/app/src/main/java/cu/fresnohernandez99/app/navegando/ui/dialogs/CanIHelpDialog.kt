package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import cu.fresnohernandez99.app.navegando.R
import cu.fresnohernandez99.app.navegando.databinding.CanIHelpLayoutBinding
import cu.fresnohernandez99.app.navegando.databinding.InformationLayoutBinding
import cu.fresnohernandez99.app.navegando.databinding.WhoDidItLayoutBinding
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener

class CanIHelpDialog(
    val context: Context,
    val inflater: LayoutInflater,
    val activity: Activity
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val canIHelpDialogBinding = CanIHelpLayoutBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    fun build() {
        dialogBuilder.setView(canIHelpDialogBinding.root)
        canIHelpDialogBinding.apply {

            goMarket.setOnClickListener {
                val clipboard =
                    ContextCompat.getSystemService(context, ClipboardManager::class.java)
                clipboard?.let {
                    val clip = ClipData.newPlainText("tarjet num", "9205129972383070")
                    it.setPrimaryClip(clip)
                }
                Toast.makeText(context, context.getString(R.string.target_num), Toast.LENGTH_SHORT).show()

                var launchIntent =
                    activity.packageManager.getLaunchIntentForPackage("cu.etecsa.cubacel.tr.tm")
                launchIntent = launchIntent
                    ?: Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.enzona.net/")
                    )
                context.startActivity(launchIntent)
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