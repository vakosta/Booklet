package com.booklet.bookletandroid.domain

import hirondelle.date4j.DateTime
import org.junit.Test

class DateHelperTest {
    @Test
    fun numBusinessDaysFrom() {
        val result = DateHelper.numBusinessDaysFrom(DateTime(2018, 9, 10, 0, 0, 0, 0),
                DateTime(2018, 9, 12, 0, 0, 0, 0), true)
        print(result)
    }
}