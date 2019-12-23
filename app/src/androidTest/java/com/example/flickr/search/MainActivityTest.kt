package com.example.flickr.search

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.flickr.R
import com.example.flickr.asPagedList
import com.example.flickr.data.Data
import com.example.flickr.data.NetworkState
import com.example.flickr.data.Photo
import com.example.flickr.photoList
import com.example.flickr.repo.SearchRepo
import com.example.flickr.utils.InjectableActivityScenario
import com.example.flickr.utils.injectableActivityScenario
import com.example.flickr.withDrawable
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var searchRepo = Mockito.mock(SearchRepo::class.java)
    private var mainVM = SearchVM(searchRepo)


    private var mockPagedList = MutableLiveData<PagedList<Photo>>()
    private var mockNetworkState = MutableLiveData<NetworkState>()
    private var mockData = Data(mockPagedList,mockNetworkState)


    lateinit var scenario: InjectableActivityScenario<MainActivity>




    @Before
    fun setUp() {


        scenario = injectableActivityScenario<MainActivity> {
            injectActivity {
                setTestViewModel(mainVM)
                Mockito.`when`(
                    mainVM.search("kittens")
                ).thenReturn(mockData)
            }
        }.launch()





    }

    @Test
    fun checkErrorState() {




        scenario.runOnMainThread {
            mockPagedList.postValue(com.example.flickr.emptyList.asPagedList())
            mockNetworkState.postValue(NetworkState.error("No Internet"))
        }


        onView(withId(R.id.llNoResults)).check(matches(isDisplayed()))
        onView(withId(R.id.rvPhotos)).check(matches(not(isDisplayed())))

        checkDetails(R.drawable.ic_error,R.string.error,R.string.try_later)
    }

    @Test
    fun checkSuccessState() {

        scenario.runOnMainThread {
            mockNetworkState.postValue(NetworkState.LOADED)
            mockPagedList.postValue(photoList.asPagedList())
        }

        onView(withId(R.id.rvPhotos)).check(matches(isDisplayed()))
        onView(withId(R.id.llNoResults)).check(matches(not(isDisplayed())))
    }



    @Test
    fun checkNoDataState() {

        scenario.runOnMainThread {
            mockPagedList.postValue(com.example.flickr.emptyList.asPagedList())
            mockNetworkState.postValue(NetworkState.noData())
        }

        onView(withId(R.id.llNoResults)).check(matches(isDisplayed()))
        onView(withId(R.id.rvPhotos)).check(matches(not(isDisplayed())))

        checkDetails(R.drawable.no_results,R.string.no_results,R.string.try_different)
    }

    private fun checkDetails( @DrawableRes icon:Int,@StringRes title: Int, @StringRes message:Int) {
        onView(withId(R.id.ivTitle)).check(
            matches(
                withText(InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(
                    title
                ))
            ))

        onView(withId(R.id.ivMessage)).check(
            matches(
                withText(InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(
                    message
                ))
            ))

        onView(withId(R.id.ivIcon)).check(
            matches(
                withDrawable(icon)
            ))

    }

    @After
    fun close() {

        scenario.close()

    }
}