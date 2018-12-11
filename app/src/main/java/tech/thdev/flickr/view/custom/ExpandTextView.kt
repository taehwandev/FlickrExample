package tech.thdev.flickr.view.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.View.OnClickListener
import kotlinx.android.synthetic.main.view_expand_text.view.*
import tech.thdev.flickr.R
import tech.thdev.flickr.util.setHtml

class ExpandTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    /* The default number of lines */
    companion object {
        private const val DEFAULT_MAX_COLLAPSED_LINES = 1
    }

    private var isCollapsed: Boolean = true

    /**
     * Default setting.
     */
    private var maxCollapsedLines = 0

    private var expandDrawable: Drawable? = null
    private var collapseDrawable: Drawable? = null

    init {
        View.inflate(context, R.layout.view_expand_text, this)

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.ExpandTextView, defStyleAttr, 0).run {
                maxCollapsedLines = getInt(R.styleable.ExpandTextView_maxCollapsedLines, DEFAULT_MAX_COLLAPSED_LINES)

                expandDrawable = getDrawable(R.styleable.ExpandTextView_expandDrawable)
                collapseDrawable = getDrawable(R.styleable.ExpandTextView_collapseDrawable)

                tv_message.run {
                    setTextColor(getColor(R.styleable.ExpandTextView_android_textColor, 0))
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimensionPixelSize(R.styleable.ExpandTextView_android_textSize, 0)
                            .toFloat())
                }
                recycle()
            }
        }
        expandDrawable.getDrawable(context, R.drawable.ic_unfold_more)
        img_expand.visibility = View.GONE
        if (isCollapsed) {
            img_expand.setImageDrawable(expandDrawable)
        }
    }

    private fun Drawable?.getDrawable(context: Context, resId: Int): Drawable {
        if (this == null) {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.resources.getDrawable(resId, context.theme)
            } else {
                context.resources.getDrawable(resId)
            }
        }
        return this
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // If no change, measure and return
        if (visibility == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        img_expand.visibility = View.GONE
        tv_message.maxLines = Integer.MAX_VALUE

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (tv_message.lineCount <= maxCollapsedLines) {
            setOnClickListener { }
            return

        } else {
            setOnClickListener(itemOnClickListener)
        }

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show button.
        if (isCollapsed) {
            tv_message.maxLines = maxCollapsedLines
            tv_message.ellipsize = TextUtils.TruncateAt.END
            img_expand.visibility = View.VISIBLE
        }

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private val itemOnClickListener = OnClickListener {
        isCollapsed = !isCollapsed

        if (isCollapsed) {
            tv_message.maxLines = maxCollapsedLines
            tv_message.ellipsize = TextUtils.TruncateAt.END

        } else {
            tv_message.maxLines = Integer.MAX_VALUE
        }

        img_expand.visibility = View.GONE
        if (isCollapsed && expandDrawable != null) {
            img_expand.setImageDrawable(expandDrawable)
            img_expand.visibility = View.VISIBLE
        }

        if (!isCollapsed && collapseDrawable != null) {
            img_expand.setImageDrawable(collapseDrawable)
            img_expand.visibility = View.VISIBLE
        }
    }

    fun setText(message: String?, isCollapsed: Boolean = true) {
        this.isCollapsed = isCollapsed

        tv_message.setHtml(message)
        tv_message.requestLayout()
    }

}