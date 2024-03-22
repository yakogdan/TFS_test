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
    private var linesList = mutableListOf<MutableList<ReactionView>>()
    private var linesHeights = mutableListOf<Int>()

    private var parentWidth = 0
    private var parentHeight = 0

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

        parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        var currentLine = mutableListOf<ReactionView>()
        var currentLineWidth = 0
        var maxHeight = 0

        linesList.clear()
        linesHeights.clear()

        reactions.forEach { reactionView ->
            measureChild(reactionView, widthMeasureSpec, heightMeasureSpec)
            if (currentLineWidth + reactionView.measuredWidth > parentWidth) {
                linesList.add(currentLine)
                linesHeights.add(maxHeight)
                currentLine = mutableListOf<ReactionView>()
                currentLineWidth = 0
                maxHeight = 0
            }

            currentLine.add(reactionView)
            currentLineWidth += reactionView.measuredWidth + reactionPadding
            maxHeight = maxOf(maxHeight, reactionView.measuredHeight)
        }

        if (currentLine.isNotEmpty()) {
            linesList.add(currentLine)
            linesHeights.add(maxHeight)
        }

        var totalHeight = paddingTop + paddingBottom
        for (height in linesHeights) {
            totalHeight += height + reactionPadding
        }
        setMeasuredDimension(parentWidth, minOf(totalHeight, parentHeight))
    }

    override fun onLayout(p0: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var currentTop = paddingTop
        var currentLeft = paddingLeft
        var rowWidthMax = 0

        for (line in linesList.indices) {
            val currentLine = linesList[line]
            val lineHeights = linesHeights[line]
            val lineWidth = currentLine.sumOf { it.measuredWidth } + (currentLine.size - 1) * reactionPadding

            if (lineWidth > rowWidthMax)
                rowWidthMax = lineWidth

            for (j in currentLine.indices) {
                val child = currentLine[j]
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                val childLeft = currentLeft
                val childTop = currentTop
                val childRight = currentLeft + childWidth
                val childBottom = currentTop + childHeight
                child.layout(childLeft, childTop, childRight, childBottom)
                currentLeft += childWidth + reactionPadding
            }

            currentTop += lineHeights + reactionPadding
            currentLeft = paddingLeft
        }
    }
}