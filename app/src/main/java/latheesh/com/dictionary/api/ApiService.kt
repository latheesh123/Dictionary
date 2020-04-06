package latheesh.com.dictionary.api

import io.reactivex.Single
import latheesh.com.dictionary.model.DictionaryResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @GET(getterKey)
    @Headers(
        headerKey,
        headerValue
    )
    fun getWordsByTerm(@Query(queryKey) term: String): Single<DictionaryResponse>

    companion object {
        const val headerValue = "x-rapidapi-host:mashape-community-urban-dictionary.p.rapidapi.com"
        const val headerKey = "x-rapidapi-key:20cb850be4msh7fbc1d97b9622cap1e814fjsn294a460ac576"
        const val queryKey = "term"
        const val getterKey = "define"

    }
}