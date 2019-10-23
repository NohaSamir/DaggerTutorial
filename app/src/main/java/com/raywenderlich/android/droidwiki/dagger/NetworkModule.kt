package com.raywenderlich.android.droidwiki.dagger

import com.raywenderlich.android.droidwiki.network.WikiApi
import com.raywenderlich.android.droidwiki.utils.Const
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val NAME_BASE_URL = "NAME_BASE_URL"
    }

    @Provides
    @Named(NAME_BASE_URL)
    fun provideBaseUrlString() = "${Const.PROTOCOL}://${Const.LANGUAGE}.${Const.BASE_URL}"


    @Provides
    @Singleton
    fun provideClient(): OkHttpClient = OkHttpClient()


    @Provides
    @Singleton
    fun provideRequestBuilder(@Named(NAME_BASE_URL) url: String): HttpUrl.Builder? = url.toHttpUrlOrNull()?.newBuilder()


    @Provides
    @Singleton
    fun provideWikiAPI(client: OkHttpClient, builder: HttpUrl.Builder?): WikiApi = WikiApi(client , builder)

}