package com.bogdankostyrko.tfstest.models

data class ReactionItem(
    val reaction: String,
    val count: Int,
) {
    override fun toString(): String {
        return "$reaction $count"
    }
}