package com.lukieoo.todolist


import android.icu.util.TimeUnit
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EsspresoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun esspresoTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.add_btn), withText("Add new Task"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment_container),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())
        java.util.concurrent.TimeUnit.SECONDS.toMillis(4)
        val textInputEditText = onView(
            allOf(
                withId(R.id.itTitle),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayout1),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("New"), closeSoftKeyboard())

        pressBack()

        val appCompatImageView = onView(
            allOf(
                withId(R.id.photoType3), withContentDescription("TODO"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardImage3),
                        0
                    ),
                    0
                )
            )
        )
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
