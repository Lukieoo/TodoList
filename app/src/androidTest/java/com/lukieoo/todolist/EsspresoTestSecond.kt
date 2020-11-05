package com.lukieoo.todolist


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
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
class EsspresoTestSecond {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun esspresoTestSecond() {
        val recyclerView = onView(
            allOf(
                withId(R.id.todo_list),
                childAtPosition(
                    withId(R.id.swipeRefreshLayout),
                    0
                )
            )
        )
        Thread.sleep(2000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

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
        appCompatImageView.perform(scrollTo(), click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.addData),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())
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
