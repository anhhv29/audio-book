package vn.gotech.audiobook.base

abstract class ClickableAdapter<T> : BaseRecyclerViewAdapter<T>() {

    var listener: BaseAdapterAction<T>? = null

    interface BaseAdapterAction<T> {
        /*
            click event to an item, can distinguish event by code
         */
        fun click(position: Int, data: T, code: Int = -1)
    }

}