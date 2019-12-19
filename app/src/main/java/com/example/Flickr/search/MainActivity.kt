package com.example.Flickr.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.res.Configuration
import android.graphics.Color
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.example.Flickr.R
import com.example.Flickr.extensions.injectViewModel
import com.example.Flickr.utils.ItemDivider
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var searchVM: SearchVM
    val adapter = SearchAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchVM = injectViewModel(viewModelFactory)


        val config = resources.configuration
        rvPhotos.layoutManager = GridLayoutManager(this,3,getOrientation(config),false)
        val divider = resources.getDimensionPixelSize(R.dimen.divider)
        rvPhotos.addItemDecoration(ItemDivider(Color.TRANSPARENT,divider,divider))

        rvPhotos.adapter = adapter

        etSearch.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && etSearch.text.toString().isNotEmpty()) {
                val query = etSearch.text.toString()
                etSearch.text?.clear()

                searchPhotos(query)
                true
            }
            true
        }

        ivSearch.setOnClickListener {
            val query = etSearch.text.toString()
            etSearch.text?.clear()

            searchPhotos(query)
        }


        searchPhotos()
    }

    private fun searchPhotos(query: String = "kittens") {
        dismissKeyboard()
        val data  =searchVM.search(query)




        data?.pagedList?.observe(this, Observer {

            adapter.submitList(it)
            if (it.size == 0) {
                llNoResults.visibility = View.VISIBLE
                rvPhotos.visibility = View.GONE
            } else {
                llNoResults.visibility = View.GONE
                rvPhotos.visibility = View.VISIBLE

            }
        })
    }


    @RecyclerView.Orientation
    private fun getOrientation(config: Configuration): Int {
        return when (config.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                LinearLayoutManager.VERTICAL
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LinearLayoutManager.HORIZONTAL
            }
            else -> {
                throw AssertionError("This should not be the case.")
            }
        }
    }

    private fun dismissKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
    }

}
