package com.example.flickr.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickr.di.ViewModelFactory
import com.example.flickr.di.ViewModelKey
import com.example.flickr.search.SearchVM
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