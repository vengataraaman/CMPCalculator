package com.vengat.calculator.util

fun String.trimValue(): String {
    if (this == "0" || this == "0.0" || this.isEmpty()) {
        return "";
    } else if (this.takeLast(2) == ".0") {
        return this.dropLast(2)
    }
    return this
}
