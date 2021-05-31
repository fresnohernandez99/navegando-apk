package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import cu.fresnohernandez99.app.navegando.databinding.ImagePreviewLayoutBinding
import cu.fresnohernandez99.app.navegando.utils.ArgConstants
import cu.fresnohernandez99.app.navegando.utils.imageLoader.ImageLoader

class ImagePreviewDialog(
    val context: Context,
    val inflater: LayoutInflater,
    val imageLoader: ImageLoader
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val imagePreviewLayoutBinding = ImagePreviewLayoutBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    fun build(imageName: String, imageType: String) {
        dialogBuilder.setView(imagePreviewLayoutBinding.root)
        imagePreviewLayoutBinding.apply {

            when (imageType) {
                ArgConstants.BITMAP -> imageLoader.loadAsset(
                    imageName,
                    imageTouchPreview,
                    false
                )
                ArgConstants.VECTOR -> imageLoader.loadDrawable(
                    imageName,
                    imageTouchPreview,
                    false
                )
            }

            closeBtn.setOnClickListener {
                dismiss()
            }
        }

        ins = dialogBuilder.create()
        ins.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        ins.setCancelable(true)
    }

    fun buildAndShow(i: String, t: String) {
        build(i, t)
        show()
    }

    fun show() = ins.show()

    fun dismiss() = ins.dismiss()

}