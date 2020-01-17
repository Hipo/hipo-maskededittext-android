package com.hipo.maskededittext

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

fun Int.checkIfInputOutputMatches(inputText: String, outputText: String) {
    onView(withId(this)).perform(typeText(inputText)).check(matches(withText(outputText)))
}
