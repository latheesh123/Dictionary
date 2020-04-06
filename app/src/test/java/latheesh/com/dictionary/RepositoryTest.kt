package latheesh.com.dictionary

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import latheesh.com.dictionary.api.ApiService
import latheesh.com.dictionary.model.DictionaryResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    lateinit var retrofit: Retrofit
    val mockWebServer = MockWebServer()
    lateinit var apiService: ApiService
    lateinit var call: Single<DictionaryResponse>
    lateinit var dictionaryResponse: DictionaryResponse

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        retrofit = Retrofit.Builder()
            .baseUrl(
                mockWebServer.url(
                    "https://mashape-community-urban-dictionary.p.rapidapi.com/"
                ).toString()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mockWebServer.enqueue(MockResponse().setBody(getJsonResult()))

    }

    @Test
    fun `test loading data`() {

        apiService = retrofit.create(ApiService::class.java)
        val resultObserver = TestObserver<DictionaryResponse>()
        call = apiService.getWordsByTerm("hello")
        call.subscribeWith(resultObserver)
        resultObserver
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()

        dictionaryResponse = resultObserver.values()[0]
        assert(dictionaryResponse.list.size == 10)

        mockWebServer.shutdown()
    }

    private fun getJsonResult(): String {
        return "{\"list\":[{\"definition\":\"The only [proper] [response] to something that [makes absolutely no sense]\",\"permalink\":\"http://wat.urbanup.com/3322419\",\"thumbs_up\":3672,\"sound_urls\":[],\"author\":\"watwat\",\"word\":\"wat\",\"defid\":3322419,\"current_vote\":\"\",\"written_on\":\"2008-09-04T00:00:00.000Z\",\"example\":\"1: If all the animals on the [equator] were capable of [flattery], Halloween and Easter would fall on the same day. 2: wat 1: Wow your cock is almost as big as my dad's. 2: wat 1: I accidentially a whole [coke bottle] 2: You accidentially what? 1: A whole coke bottle 2: wat\",\"thumbs_down\":424}]}"
    }

}