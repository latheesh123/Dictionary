package latheesh.com.dictionary

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import latheesh.com.dictionary.ui.DictionaryActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*


@LargeTest
@RunWith(AndroidJUnit4::class)
class DictionaryActivityTest {

    @get:Rule
    val mActivityRule = ActivityTestRule<DictionaryActivity>(DictionaryActivity::class.java)

    @Test
    fun testUi() {

        onView(withId(R.id.dictionary_progressbar_view))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.dictionary_search_editText))
            .perform(typeText("Android"), closeSoftKeyboard())
        Thread.sleep(2000)
        assert(mActivityRule.activity.isInternetAvailable(context = mActivityRule.activity))
        assert(getCount() > 0)

        onView(withId(R.id.dictionary_recycler_view)).perform(ViewActions.swipeUp());
        Thread.sleep(1500)

        onView(withId(R.id.dictionary_recycler_view)).perform(ViewActions.swipeDown());

        onView(withId(R.id.dictionary_progressbar_view))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        Thread.sleep(1500)

        onView(withId(R.id.dictionary_thumbsup_view)).perform(ViewActions.click())
        Thread.sleep(1500)
        onView(withId(R.id.dictionary_thumbsdown_view)).perform(ViewActions.click())

        assert(getCount() > 0)
    }

    private fun getCount(): Int {
        val recyclerView =
            mActivityRule.activity.findViewById(R.id.dictionary_recycler_view) as RecyclerView
        return recyclerView.adapter?.itemCount ?: 0
    }

}