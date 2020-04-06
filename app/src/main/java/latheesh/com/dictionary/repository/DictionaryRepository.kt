package latheesh.com.dictionary.repository

import io.reactivex.Observable
import io.reactivex.Single
import latheesh.com.dictionary.api.ApiService
import latheesh.com.dictionary.model.ResultData
import javax.inject.Inject


open class DictionaryRepository @Inject constructor(private var dictionaryService: ApiService) {

    fun getTermsList(term: String): Single<ArrayList<ResultData>> {
        return dictionaryService.getWordsByTerm(term)
            .flatMap { response ->
                Observable.just(response.list)
                    .flatMapIterable { data -> data }
                    .map { listitem ->
                        ResultData(
                            listitem.defid ?: 0,
                            listitem.definition ?: "",
                            listitem.thumbsUp ?: 0,
                            listitem.thumbsDown ?: 0,
                            listitem.word ?: "",
                            listitem.author ?: "",
                            listitem.example ?: ""
                        )
                    }
                    .toList()
                    .map { ArrayList(it) }
            }
    }

}
