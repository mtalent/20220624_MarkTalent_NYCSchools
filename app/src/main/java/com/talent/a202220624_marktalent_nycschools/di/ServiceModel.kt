package com.talent.a202220624_marktalent_nycschools.di


import com.talent.a202220624_marktalent_nycschools.api.ApiService
import com.talent.a202220624_marktalent_nycschools.api.ApiService.Companion.BASE_URL
import com.talent.a202220624_marktalent_nycschools.model.Repository
import com.talent.a202220624_marktalent_nycschools.model.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Framework that interacts with the NYC OpenData API.
@Module
@InstallIn(SingletonComponent::class)
class ServiceModel {

    @Provides
    fun provideNycService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    fun provideRepositoryLayer(service: ApiService): Repository =
        RepositoryImpl(service)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher)
}