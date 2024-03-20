package com.bogdankostyrko.tfstest.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val widthFb = first.measuredWidth + second.measuredWidth + paddingLeft + paddingRight
        val heightFb =
            maxOf(first.measuredHeight, second.measuredHeight) + paddingTop + paddingBottom

        setMeasuredDimension(widthFb, heightFb)

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

        val firstLeft = paddingLeft
        val firstRight = firstLeft + first.measuredWidth

        first.layout(
            firstLeft,
            paddingTop,
            firstRight,
            first.measuredHeight + paddingTop + paddingBottom
        )

        second.layout(
            firstRight,
            paddingTop,
            firstRight + second.measuredWidth,
            second.measuredHeight + paddingBottom
        )

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

    companion object {

        const val MESSAGE_WIDTH = 110f
    }
}