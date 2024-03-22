package com.bogdankostyrko.tfstest.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.bogdankostyrko.tfstest.R
import com.bogdankostyrko.tfstest.models.ReactionItem

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0,
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private val reactions = mutableListOf<ReactionView>()
    private val reactionPadding = context.resources.getDimension(R.dimen.reaction_view_padding).toInt()
    private var lineList = mutableListOf<MutableList<ReactionView>>()
    private var lineHeights = mutableListOf<Int>()

    fun addReaction(reaction: ReactionItem) {
        val reactionView = ReactionView(context, reactionItem = reaction).apply {

            layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

            setPadding(
                reactionPadding,
                reactionPadding,
                reactionPadding,
                reactionPadding
            )
        }

        reactions.add(reactionView)
        addView(reactionView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        var maxHeight = 0
        var currentWidth = 0
        var currentRow = mutableListOf<ReactionView>()

        lineList.clear()
        lineHeights.clear()

        reactions.forEach { reactionView ->
            measureChild(reactionView, widthMeasureSpec, heightMeasureSpec)
            if (currentWidth + reactionView.measuredWidth > parentWidth) {
                lineList.add(currentRow)
                lineHeights.add(maxHeight)
                currentRow = mutableListOf()
                currentWidth = 0
                maxHeight = 0
            }

            currentRow.add(reactionView)
            currentWidth += reactionView.measuredWidth + reactionPadding
            maxHeight = maxOf(maxHeight, reactionView.measuredHeight)
        }

        if (currentRow.isNotEmpty()) {
            lineList.add(currentRow)
            lineHeights.add(maxHeight)
        }

        var totalHeight = paddingTop + paddingBottom
        for (height in lineHeights) {
            totalHeight += height + reactionPadding
        }
        setMeasuredDimension(parentWidth, minOf(totalHeight, parentHeight))
    }

    override fun onLayout(p0: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var currentTop = paddingTop
        var currentLeft = paddingLeft
        var rowWidthMax = 0

        for (i in lineList.indices) {
            val currentRow = lineList[i]
            val rowHeight = lineHeights[i]
            val rowWidth = currentRow.sumOf { it.measuredWidth } + (currentRow.size - 1) * reactionPadding

            if (rowWidth > rowWidthMax)
                rowWidthMax = rowWidth

            for (j in currentRow.indices) {
                val child = currentRow[j]
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                val childLeft = currentLeft
                val childTop = currentTop
                val childRight = currentLeft + childWidth
                val childBottom = currentTop + childHeight
                child.layout(childLeft, childTop, childRight, childBottom)
                currentLeft += childWidth + reactionPadding
            }

            currentTop += rowHeight + reactionPadding
            currentLeft = paddingLeft
        }
    }
}