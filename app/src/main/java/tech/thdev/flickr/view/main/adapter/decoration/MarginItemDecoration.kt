package tech.thdev.flickr.view.main.adapter.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class MarginItemDecoration(private val space: Int,
                           private val passPosition: Int = 1) : RecyclerView.ItemDecoration() {

    private val comparisonValue = if (passPosition % 2 == 0) 0 else 1

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.run {
            val adapterPosition = parent.getChildAdapterPosition(view)
            if (passPosition <= adapterPosition) {
                if (parent.getChildAdapterPosition(view) % 2 == comparisonValue) {
                    left = space
                } else {
                    right = space
                }
            }
        }
    }
}