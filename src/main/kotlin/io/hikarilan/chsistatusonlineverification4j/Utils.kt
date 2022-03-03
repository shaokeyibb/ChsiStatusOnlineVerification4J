package io.hikarilan.chsistatusonlineverification4j

import java.util.*
import java.util.regex.Pattern

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun checkPatternOrGet(pattern: Pattern, input: String): String {
    return pattern.matcher(input).let {
        if (it.find()) {
            input
        } else {
            throw IllegalArgumentException("Invalid input string $input for pattern ${pattern.pattern()}")
        }
    }
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun String.toDate(customPattern: Pattern = compiledTimePattern): Date {
    return customPattern.matcher(this).let {
        if (it.find()) {
            val year = it.group(1).toInt()
            val month = it.group(2).toInt()
            val day = it.group(3).toInt()
            Calendar.getInstance().apply {
                set(year, month - 1, day)
            }.time
        } else {
            throw IllegalArgumentException("Invalid date string: $this")
        }
    }
}
