package latheesh.com.dictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import latheesh.com.dictionary.model.ResultData
import latheesh.com.dictionary.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryViewModel @Inject
constructor() : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null
    private var dictionaryTerms = MutableLiveData<ArrayList<ResultData>>()
    private var isLoading = MutableLiveData<Boolean>()
    private var isError = MutableLiveData<Boolean>()

    @Inject
    lateinit var dictionaryRepository: DictionaryRepository

    init {
        compositeDisposable = CompositeDisposable()
    }

    fun getDictionaryItems(): LiveData<ArrayList<ResultData>> {
        return dictionaryTerms
    }

    fun fetchDictionaryTerms(term: String) {
        compositeDisposable.let {
            it?.add(
                dictionaryRepository.getTermsList(term)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(DictionaryChannelsSingleDisposableObserver())
            )
        }

    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun isError(): LiveData<Boolean> {
        return isError
    }

    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable != null) {
            compositeDisposable!!.clear()
        }
        compositeDisposable = null
    }

    private inner class DictionaryChannelsSingleDisposableObserver :
        DisposableSingleObserver<ArrayList<ResultData>>() {
        override fun onStart() {
            isLoading.value = true
            isError.value = false
        }

        override fun onSuccess(t: ArrayList<ResultData>) {
            isError.value = false
            isLoading.value = false
            dictionaryTerms.value = t
        }

        override fun onError(e: Throwable) {
            isError.value = true
            isLoading.value = false
        }

    }

}