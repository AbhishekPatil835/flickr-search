package com.example.Flickr.di

import android.app.Application
import com.example.Flickr.FlickrApp
import com.example.Flickr.di.modules.ActivityModule
import com.example.Flickr.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class]
)
interface AppComponent {


    @Component.Builder
    interface Builder {


        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appModule(application: AppModule): Builder

        fun build(): AppComponent


    }

    fun inject(flickrApp: FlickrApp)


}