package com.example.Flickr

import android.app.Activity
import android.app.Application
import com.example.Flickr.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class FlickrApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

}