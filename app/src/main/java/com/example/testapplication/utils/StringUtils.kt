package com.example.testapplication.utils

import android.util.Log

fun String.ifEmptySubstituteTo(default: String): String {
    return if (this.isNullOrEmpty()) default
    else this
}

fun String.castDate(delimiter: String): String {
    val array = this.split(delimiter)
    return array[0]
}