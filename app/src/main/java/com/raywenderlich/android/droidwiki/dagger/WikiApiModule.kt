package com.raywenderlich.android.droidwiki.dagger

import com.raywenderlich.android.droidwiki.network.Homepage
import com.raywenderlich.android.droidwiki.network.Wiki
import com.raywenderlich.android.droidwiki.network.WikiApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WikiApiModule {


    @Provides
    @Singleton
    fun provideHomePageRepository(wikiApi: WikiApi): Homepage = Homepage(wikiApi)

    @Provides
    @Singleton
    fun provideWikiRepository(wikiApi: WikiApi): Wiki = Wiki(wikiApi)


}