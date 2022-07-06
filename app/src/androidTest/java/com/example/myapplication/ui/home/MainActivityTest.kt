package com.example.myapplication.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import org.hamcrest.Matchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
internal class MainActivityTest {

    @get:Rule
    private val activityTestRule = activityScenarioRule<MainActivity>()

    @Before
    fun setUp() {

    }

    @Test
    fun testClick(){
        onView(withId(R.id.btnUpdate)).perform(click())

        onView(withId(R.id.tvHello))
            .check(matches(
                withText(containsString("Hello Main World!"))
            ))
    }


}