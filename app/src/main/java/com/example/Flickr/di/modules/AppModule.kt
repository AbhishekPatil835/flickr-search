package com.example.Flickr.di.modules

import com.example.Flickr.data.FlickrApi
import com.example.Flickr.di.NetworkModule
import com.example.Flickr.repo.remote.SearchRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, VMModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideGithubService(okhttpClient: OkHttpClient,
                             converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, FlickrApi::class.java)

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FlickrApi.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(flickrApi: FlickrApi)
            = SearchRemoteDataSource(flickrApi)

    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    private fun <T> provideService(okhttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }



}