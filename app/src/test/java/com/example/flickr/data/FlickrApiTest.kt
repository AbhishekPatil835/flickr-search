package com.example.flickr.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class FlickrApiTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: FlickrApi

    private lateinit var mockWebServer: MockWebServer

    val map = HashMap<String, String>()

    init {
        map["method"] = "flickr.photos.search"
        map["api_key"] = "3e7cc266ae2b0e0d78e279ce8e361736"
        map["format"] = "json"
    }

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlickrApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }


    @Test
    fun search() {

        enqueueResponse("search_photos_response.json")
        map["text"] = "mountains"
        val resultResponse = service.searchPhotos(5, 1, map, 1).execute().body()

        /*check request type and it's end point*/
        val request = mockWebServer.takeRequest()
        Assert.assertThat(
            request.path,
            `is`("/?per_page=5&page=1&method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&text=mountains&nojsoncallback=1")
        )

        /* assert response */
        Assert.assertNotNull(resultResponse)
        val response = resultResponse!!.photos
        Assert.assertEquals(response.page, 1)
        Assert.assertEquals(response.pages, 28945)
        Assert.assertEquals(response.perpage, 5)
        Assert.assertEquals(response.total, 144724)

        Assert.assertThat(response.photos.size, `is`(5))

        val searchResult = response.photos[0]

        Assert.assertThat(searchResult.id, `is`("49242902122"))
        Assert.assertThat(searchResult.owner, `is`("22539273@N00"))
        Assert.assertThat(searchResult.secret, `is`("c5b6c48cf5"))
        Assert.assertThat(searchResult.farm, `is`(66))
        Assert.assertThat(
            searchResult.title,
            `is`("Car park @ Secteur du Berger @ Semnoz @ Annecy")
        )
        Assert.assertThat(searchResult.isfamily, `is`(0))
        Assert.assertThat(searchResult.isfriend, `is`(0))
        Assert.assertThat(searchResult.ispublic, `is`(1))


    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(
                source.readString(Charsets.UTF_8)
            )
        )
    }
}