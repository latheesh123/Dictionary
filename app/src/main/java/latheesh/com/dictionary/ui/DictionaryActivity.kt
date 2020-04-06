package latheesh.com.dictionary.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_dictionary.*
import latheesh.com.dictionary.R
import latheesh.com.dictionary.adapter.DictionaryAdapter
import latheesh.com.dictionary.di.ViewModelFactory
import latheesh.com.dictionary.model.ResultData
import latheesh.com.dictionary.viewmodel.DictionaryViewModel
import javax.inject.Inject

class DictionaryActivity :
    DaggerAppCompatActivity(),
    TextWatcher {


    @set:Inject
    lateinit var viewModelFactory: ViewModelFactory

    var dictionaryViewModel: DictionaryViewModel? = null
    var dictionaryAdapter: DictionaryAdapter = DictionaryAdapter()
    var termList: List<ResultData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        //Intiliaze the UI
        initializeUi()

        //Check for interenet connection
        checkInternetConnection()
        dictionaryViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DictionaryViewModel::class.java)
        //initialize observers for data
        initializeObservers()

    }

    private fun initializeUi() {
        setSupportActionBar(dictionary_toolbar_layout)

        //Edit text on textchanged listener
        dictionary_search_editText.addTextChangedListener(this)

        //Thumsup and Down OnclickListener
        dictionary_thumbsup_view.setOnClickListener { sortDictionaryData(THUMBS_UP) }
        dictionary_thumbsdown_view.setOnClickListener { sortDictionaryData(THUMBS_DOWN) }

        //Recyclerview Intialization
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        dictionary_recycler_view.layoutManager = layoutManager
        dictionaryAdapter.setHasStableIds(true)
        dictionary_recycler_view.adapter = dictionaryAdapter

        //Request focus on edit text on start up
        dictionary_search_editText.requestFocus()

    }

    private fun initializeObservers() {

        //observe viewmodel on loading
        dictionaryViewModel.let {
            it?.isLoading()?.observe(this,
                Observer<Boolean> { loading ->
                    dictionary_progressbar_view.visibility =
                        if (loading) View.VISIBLE else View.GONE
                    if (loading) {
                        dictionary_recycler_view.visibility = View.GONE
                        dictionary_no_word_text.visibility = View.GONE
                    }
                })
        }

        dictionaryViewModel.let {
            it?.isError()?.observe(this,
                Observer<Boolean> { isError ->
                    if (isError) {
                        dictionary_no_word_text.visibility = View.GONE
                        dictionary_progressbar_view.visibility = View.GONE
                        checkInternetConnection()
                    }
                })

        }

        dictionaryViewModel.let {
            it?.getDictionaryItems()?.observe(this,
                Observer<ArrayList<ResultData>> { resultData ->
                    dictionary_no_word_text.visibility =
                        if (resultData.isEmpty()) View.VISIBLE else View.GONE
                    dictionary_recycler_view.visibility =
                        if (resultData.isEmpty()) View.GONE else View.VISIBLE
                    termList = resultData
                    sortDictionaryData(DEFAULT)

                })
        }
    }

    //Sort the dictionary data array based on Thumsup & Down
    private fun sortDictionaryData(sortedBy: Int) {
        var dictionaryData = termList
        when (sortedBy) {
            THUMBS_DOWN -> {
                dictionaryData = termList.sortedByDescending { it.thumbDown }
            }
            THUMBS_UP -> {
                dictionaryData = termList.sortedByDescending { it.thumbUp }
            }
            DEFAULT -> {
                dictionaryData = termList
            }
        }

        //Set data to adapter after data sort
        dictionaryAdapter.setData(dictionaryData)
    }


    //Text changed listener(calls Api)
    override fun afterTextChanged(editable: Editable?) {
        dictionaryViewModel.let {
            it?.fetchDictionaryTerms(editable.toString())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    //Check for internet connection
    @Suppress("DEPRECATION")
     fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    //Provide toast message when there is no internet connection available
     fun checkInternetConnection() {
        if (!isInternetAvailable(applicationContext)) {
            Toast.makeText(
                applicationContext,
                getString(R.string.check_internet),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //Companion objects
    companion object {
        const val DEFAULT = 0
        const val THUMBS_UP = 1
        const val THUMBS_DOWN = 2
    }

}
