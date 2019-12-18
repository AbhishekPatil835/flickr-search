package com.example.Flickr.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.res.Configuration
import android.graphics.Color
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

        searchPhotos()
    }

    private fun searchPhotos(query: String = "nature") {
        val data  =searchVM.search(query)



        data?.pagedList?.observe(this, Observer {
            adapter.submitList(it)
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

}
