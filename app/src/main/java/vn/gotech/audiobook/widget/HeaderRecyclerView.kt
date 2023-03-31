package vn.gotech.audiobook.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class HeaderRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

//    fun setLabel(label: CharSequence) {
//        view_label.setText(label)
//    }
//
//    fun getLabel() = view_label
//
//    fun getList() = view_list

}