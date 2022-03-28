package com.example.testapplication.ui.news

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.data.NewsResponse
import com.example.testapplication.ui.news.recycler.NewsAdapter
import com.example.testapplication.ui.news.recycler.NewsSelectedListener
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFragment : DaggerFragment(), NewsSelectedListener {
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val newsViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[NewsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView(view)
        observeViewModel()
        getHeadlinesNews("")
        setEditText(view)
    }

    private fun setEditText(view: View) {
        val userText: EditText = view.findViewById(R.id.user_text)
        userText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val input = userText.text.toString()
                hideKeyboard()
                Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                newsClean(true)
                getHeadlinesNews(input)
                setupRecyclerView(view)
                userText.text.clear()
                userText.isCursorVisible = false
                true
            }; false
        })
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            newsViewModel.newsState.collectLatest {
                when (it) {
                    is NewsState.Success -> newsSuccess(it.newsResponse)
                    is NewsState.Failure -> Log.d("newsApi", "Failure getNews")
                    is NewsState.Loading -> Log.d("newsApi", "Loading getNews")
                    is NewsState.None -> {}
                }
            }
        }
    }

    private fun getHeadlinesNews(userInput: String) = newsViewModel.getNewsHeadlines(userInput)

    override fun onNewsSelected(url: String) {
        val openUrl = Intent(Intent.ACTION_VIEW)
        openUrl.data = Uri.parse(url)
        startActivity(openUrl)
    }

    private fun newsSuccess(newsResponse: NewsResponse) {
        newsAdapter.setNewsItems(newsResponse.articles)
    }

    private fun newsClean(clear: Boolean) {
        newsAdapter.updatedata(clear)
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = newsAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}