package tech.thdev.flickr.view.main.adapter.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class MarginItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.run {
            if (parent.getChildAdapterPosition(view) % 2 == 0) {
                left = space
            } else {
                right = space
            }
        }
    }
}