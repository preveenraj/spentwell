package com.spentwell.utils

fun Collection<*>?.isNotNullOrEmpty(): Boolean {
    return !(this.isNullOrEmpty())
}

fun String?.isNotNullOrEmpty(): Boolean {
    return !(this.isNullOrEmpty())
}