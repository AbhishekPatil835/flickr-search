package com.example.Flickr.search

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.Flickr.R
import com.example.Flickr.asPagedList
import com.example.Flickr.data.Data
import com.example.Flickr.data.NetworkState
import com.example.Flickr.data.Photo
import com.example.Flickr.photoList
import com.example.Flickr.repo.SearchRepo
import com.example.Flickr.utils.InjectableActivityScenario
import com.example.Flickr.utils.injectableActivityScenario
import kotlinx.android.synthetic.main.activity_main.view.*
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

    private lateinit var pagedList : PagedList<Photo>
    private lateinit var emptyPagedList: PagedList<Photo>

    lateinit var scenario: InjectableActivityScenario<MainActivity>

    @Before
    fun setUp() {



        pagedList = photoList.asPagedList()!!
        emptyPagedList = com.example.Flickr.emptyList.asPagedList()!!
        scenario = injectableActivityScenario<MainActivity> {
            injectActivity {
                setTestViewModel(mainVM)
                Mockito.`when`(mainVM.search("kittens")).thenReturn(mockData).then {
                    Log.e("Test"," test mock called ")
                }
            }
        }.launch()


    }

    @Test
    fun checkErrorState() {

        mockPagedList.postValue(emptyPagedList)
        mockNetworkState.postValue(NetworkState.error("No Internet"))

        onView(withId(R.id.llNoResults)).check(matches(isDisplayed()))
        onView(withId(R.id.rvPhotos)).check(matches(not(isDisplayed())))
    }

    @Test
    fun checkSuccessState() {

        mockNetworkState.postValue(NetworkState.LOADED)
        mockPagedList.postValue(pagedList)

        onView(withId(R.id.rvPhotos)).check(matches(isDisplayed()))
        onView(withId(R.id.llNoResults)).check(matches(not(isDisplayed())))
    }

    @Test
    fun checkNoDataState() {

        mockPagedList.postValue(emptyPagedList)
        mockNetworkState.postValue(NetworkState.noData())

        onView(withId(R.id.llNoResults)).check(matches(isDisplayed()))
        onView(withId(R.id.rvPhotos)).check(matches(not(isDisplayed())))
    }

    @After
    fun close() {
        scenario.close()

    }
}