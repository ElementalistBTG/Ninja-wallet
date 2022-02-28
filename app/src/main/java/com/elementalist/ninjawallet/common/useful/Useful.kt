package com.elementalist.ninjawallet.common.useful

fun Int.formatWithThousandComma(): String {
    val result = StringBuilder()
    val size = this.toString().length
    return if (size > 3) {
        for (i in size - 1 downTo 0) {
            result.insert(0, this.toString()[i])
            if ((i != size - 1) && i != 0 && (size - i) % 3 == 0)
                result.insert(0, ".")
        }
        result.toString()
    } else
        this.toString()
}