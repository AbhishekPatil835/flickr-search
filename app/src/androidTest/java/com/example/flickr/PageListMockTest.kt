package com.example.flickr

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PagedListMockTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun mockPagedListWorks() {

        val pagedList = photoList.asPagedList()

        assertEquals(photoList, pagedList?.toList())
    }
}