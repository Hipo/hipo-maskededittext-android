package com.hipo.maskededittext

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UiTests {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun isPhoneMaskWorking() {
        val inputText = "1234567890"
        val expectedResult = "(123) 456-7890"
        R.id.phoneEditText.checkIfInputOutputMatches(inputText, expectedResult)
    }

    @Test
    fun isDateMaskWorking() {
        val inputText = "12122001"
        val expectedResult = "12 / 12 / 2001"
        R.id.dateEditText.checkIfInputOutputMatches(inputText, expectedResult)
    }

    @Test
    fun isSsnMaskWorking() {
        val inputText = "123121234"
        val expectedResult = "123-12-1234"
        R.id.ssnEditText.checkIfInputOutputMatches(inputText, expectedResult)
    }

    @Test
    fun isDollarSignStaticMaskWorking() {
        val inputText = "1234"
        val expectedResult = "$1234"
        R.id.staticTextEditText.checkIfInputOutputMatches(inputText, expectedResult)
    }

    @Test
    fun isCustomCreditCardMaskWorking() {
        val inputText = "1234123412341234"
        val expectedResult = "1234-1234-1234-1234"
        R.id.customEditText.checkIfInputOutputMatches(inputText, expectedResult)
    }
}
