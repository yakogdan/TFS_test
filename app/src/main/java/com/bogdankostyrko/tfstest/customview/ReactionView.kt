package com.bogdankostyrko.tfstest.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.bogdankostyrko.tfstest.R
import com.bogdankostyrko.tfstest.models.ReactionItem

class ReactionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0,
) : View(context, attributeSet, defStyle, defTheme) {

    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0,
        reactionItem: ReactionItem
    ) : this(context, attrs, defStyleAttr, defStyleRes) {
        reactionToDraw = reactionItem
    }

    private var reactionToDraw: ReactionItem

    init {
        reactionToDraw = run {
            var emojiXml = ""
            var countXml = 0
            context.withStyledAttributes(attributeSet, R.styleable.ReactionView) {
                emojiXml = getString(R.styleable.ReactionView_emoji) ?: ""
                countXml = getInt(R.styleable.ReactionView_count, 0)
            }
            ReactionItem(emojiXml, countXml)
        }
    }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 14f.sp(context)
    }

    private val textRect = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(
            reactionToDraw.toString(),
            0,
            reactionToDraw.toString().length,
            textRect
        )

        val actualWidth =
            resolveSize(paddingRight + paddingLeft + textRect.width(), widthMeasureSpec)

        val actualHeight =
            resolveSize(paddingTop + paddingBottom + textRect.height(), heightMeasureSpec)

        setMeasuredDimension(
            actualWidth,
            actualHeight
        )
    }

    override fun onDraw(canvas: Canvas) {
        val topOffset = height / 2 - textRect.exactCenterY()
        canvas.drawText(reactionToDraw.toString(), paddingLeft.toFloat(), topOffset, textPaint)
    }
}