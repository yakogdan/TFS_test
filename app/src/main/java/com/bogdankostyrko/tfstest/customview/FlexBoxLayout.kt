package com.bogdankostyrko.tfstest.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0,
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

//    private val reactions = mutableListOf<ReactionView>()

//    init {
//        inflate(context, R.layout.reactions_flex_box, this)
//    }

//    fun addReaction(reaction: ReactionItem) {
//        val reactionView = ReactionView(context, reactionItem = reaction).apply {
//
//            layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//
//            setPadding(
//                5f.dp(context),
//                5f.dp(context),
//                8f.dp(context),
//                7f.dp(context)
//            )
//
//            (layoutParams as MarginLayoutParams).setMargins(
//                5f.dp(context),
//                3f.dp(context),
//                5f.dp(context),
//                3f.dp(context)
//            )
//        }
//
//        reactions.add(reactionView)
//        addView(reactionView)
//    }

    val first
        get() = getChildAt(0)
    val second
        get() = getChildAt(1)

    private var severalLines: Boolean = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        measureChildWithMargins(first, widthMeasureSpec, 0, heightMeasureSpec, 0)
        measureChildWithMargins(second, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)

        val firstWidth = first.measuredWidth + first.marginLeft + first.marginRight
        val secondWidth = second.measuredWidth + second.marginLeft + second.marginRight

        val wantedWidthFb = paddingLeft + paddingRight + firstWidth + secondWidth

        val actualWidth = if (wantedWidthFb <= parentWidth) {
            severalLines = false
            wantedWidthFb
        } else {
            severalLines = true
            maxOf(firstWidth, secondWidth) + paddingLeft + paddingRight
        }

        val actualHeight = if (severalLines) {
            first.measuredHeight + first.marginTop + first.marginBottom +
                    second.measuredHeight + second.marginTop + second.marginBottom
        } else {
            maxOf(
                first.measuredHeight + first.marginTop + first.marginBottom,
                second.measuredHeight + second.marginTop + second.marginBottom
            )
        } + paddingTop + paddingBottom


        setMeasuredDimension(actualWidth, actualHeight)

//        reactions.forEach { reactionView ->
//
//            measureChildWithMargins(
//                reactionView,
//                widthMeasureSpec,
//                0,
//                heightMeasureSpec,
//                0
//            )
//
//
//        }


//        var actualHeight = paddingTop + paddingBottom
//        var actualWidth = paddingLeft + paddingRight
//
//        var maxWidth = 0
//        var maxLineHeight = 0
//
//        reactions.forEach { emojiView ->
//
//            measureChildWithMargins(emojiView, widthMeasureSpec, 0, heightMeasureSpec, 0)
//
//            val viewHeight = emojiView.measuredHeight + emojiView.marginTop + emojiView.marginBottom
//
//            if (maxLineHeight < viewHeight) {
//                maxLineHeight = viewHeight
//            }
//
//            if (actualHeight == paddingTop + paddingBottom) {
//                actualHeight += maxLineHeight
//            }
//
//            if (actualWidth > MESSAGE_WIDTH.dp(context)) {
//                actualWidth = paddingLeft + paddingRight
//                actualHeight += maxLineHeight
//            }
//
//            val viewWidth = emojiView.measuredWidth + emojiView.marginLeft + emojiView.marginRight
//            actualWidth += viewWidth
//
//            if (maxWidth < actualWidth) {
//                maxWidth = actualWidth
//            }
//        }
//        setMeasuredDimension(maxWidth, actualHeight)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        val firstLeft = paddingLeft + first.marginLeft
        val firstRight = firstLeft + first.measuredWidth
        val firstTop = paddingTop + first.marginTop
        val firstBottom = firstTop + first.measuredHeight

        first.layout(
            firstLeft,
            firstTop,
            firstRight,
            firstBottom
        )

        if (severalLines) {
            second.layout(
                paddingLeft + second.marginLeft,
                firstBottom + first.marginBottom + second.marginTop,
                paddingLeft + second.marginLeft + second.measuredWidth,
                firstBottom + first.marginBottom + second.marginTop + second.measuredHeight
            )
        } else {
            second.layout(
                firstRight + first.marginRight + second.marginLeft,
                paddingTop + second.marginTop,
                firstRight + first.marginRight + second.marginLeft + second.measuredWidth,
                paddingTop + second.marginTop + second.measuredHeight
            )
        }

//        var offsetWidth = paddingLeft
//        if (reactions.isNotEmpty()) {
//            offsetWidth += reactions.first().marginLeft
//        }
//
//        var offsetHeight = paddingTop
//
//        var maxHeight = 0
//
//        reactions.forEach { emojiView ->
//
//            val viewHeightWithMargin = emojiView.measuredHeight + emojiView.marginTop
//
//            if (maxHeight < viewHeightWithMargin) {
//                maxHeight = viewHeightWithMargin
//            }
//
//            if (offsetWidth >= MESSAGE_WIDTH.dp(context)) {
//                offsetWidth = paddingLeft + emojiView.marginLeft
//                offsetHeight += maxHeight
//            }
//
//            emojiView.layout(
//                offsetWidth,
//                offsetHeight + emojiView.marginTop,
//                offsetWidth + emojiView.measuredWidth,
//                offsetHeight + maxHeight
//            )
//
//            offsetWidth += emojiView.measuredWidth + emojiView.marginLeft + emojiView.marginRight
//        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}