package com.example.Flickr.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.Flickr.di.ViewModelFactory
import com.example.Flickr.di.ViewModelKey
import com.example.Flickr.search.SearchVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VMModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(SearchVM::class)
    abstract fun bindSearchVM(mainVM: SearchVM) : ViewModel
}