package com.grok.akm.sphtest.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.grok.akm.sphtest.ViewModel.ViewModelFactory
import com.grok.akm.sphtest.api.ApiCallInterface
import com.grok.akm.sphtest.db.DataDB
import com.grok.akm.sphtest.db.DataDao
import com.grok.akm.sphtest.repository.Repository
import com.grok.akm.sphtest.util.Constants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule(private val application: MyApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    internal fun getRequestHeader(): OkHttpClient {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Constants.DATA_GOV_SG_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    internal fun getApiCallInterface(retrofit: Retrofit): ApiCallInterface {
        return retrofit.create(ApiCallInterface::class.java)
    }

    @Provides
    @Singleton
    internal fun getDataDB(application: Application) : DataDB {
        return DataDB.getDatabase(application)!!
    }

    @Provides
    @Singleton
    internal fun getDataDao(dataDB: DataDB) : DataDao {
        return dataDB.dataDao()
    }

    @Provides
    @Singleton
    internal fun getRepository(apiCallInterface: ApiCallInterface, dataDao: DataDao): Repository {
        return Repository(apiCallInterface,dataDao)
    }

    @Provides
    @Singleton
    internal fun getViewModelFactory(myRepository: Repository): ViewModelProvider.Factory {
        return ViewModelFactory(myRepository)
    }

}