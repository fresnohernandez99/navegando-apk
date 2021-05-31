package cu.fresnohernandez99.app.navegando.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import cu.fresnohernandez99.app.navegando.data.model.entities.Question
import cu.fresnohernandez99.app.navegando.databinding.QuestionLayoutBinding
import cu.fresnohernandez99.app.navegando.ui.adapters.QuestionOptionsAdapter
import cu.fresnohernandez99.app.navegando.ui.adapters.RecyclerViewClickListener

class QuestionOptionsDialog(
    val context: Context,
    val inflater: LayoutInflater,
    val listener: RecyclerViewClickListener
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val questionLayoutBinding: QuestionLayoutBinding =
        QuestionLayoutBinding.inflate(inflater)
    private val adapter = QuestionOptionsAdapter(listener)
    private lateinit var ins: AlertDialog

    fun build(q: Question) {
        dialogBuilder.setView(questionLayoutBinding.root)
        questionLayoutBinding.apply {
            question = q
            adapterV = adapter
        }
        adapter.setList(q.options)
        ins = dialogBuilder.create()
        ins.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun buildAndShow(q: Question){
        build(q)
        show()
    }

    fun show() = ins.show()

    fun dismiss() = ins.dismiss()

}