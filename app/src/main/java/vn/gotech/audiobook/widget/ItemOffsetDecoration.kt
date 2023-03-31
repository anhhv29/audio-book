package vn.gotech.audiobook.widget

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(var spaceInPixel: Int, var hasVerticalPadding: Boolean = true, var hasHorizontalPadding: Boolean = true) : RecyclerView.ItemDecoration() {

    constructor(
        @NonNull context: Context,
        @DimenRes spaceDimenRes: Int,
        hasVerticalPadding: Boolean = true,
        hasHorizontalPadding: Boolean = true
    ) : this(
            context.resources.getDimensionPixelSize(spaceDimenRes),
            hasVerticalPadding,
            hasHorizontalPadding
    )

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.set(
            if (hasHorizontalPadding) spaceInPixel else 0,
            if (hasVerticalPadding) spaceInPixel else 0,
            if (hasHorizontalPadding) spaceInPixel else 0,
            if (hasVerticalPadding) spaceInPixel else 0
        )
    }
}