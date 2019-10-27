package com.grok.akm.sphtest.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.grok.akm.sphtest.MobileDataUsageListHelper
import com.grok.akm.sphtest.api.ApiCallInterface
import com.grok.akm.sphtest.db.DataDB
import com.grok.akm.sphtest.db.DataDao
import com.grok.akm.sphtest.model.MobileDataResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@SmallTest
class RepositoryUnitTest {

    @get:Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    lateinit var apiCallInterface: ApiCallInterface

    private lateinit var database: DataDB

    lateinit var dataDao: DataDao


    lateinit var repository: Repository

    private val data = Gson().fromJson(MobileDataUsageListHelper.json1, MobileDataResponse::class.java)

    private val mockServer = MockWebServer()
    private val mockedResponse1 = MockResponse()
    private val mockedResponse2 = MockResponse()


    @Before
    @Throws(Exception::class)
    fun setUp() {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataDB::class.java
        ).allowMainThreadQueries().build()

        apiCallInterface = retrofit.create(ApiCallInterface::class.java)

        dataDao = database.dataDao()


        repository = Repository(apiCallInterface,dataDao)


        mockedResponse1.setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MobileDataUsageListHelper.json0)

        mockedResponse2.setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MobileDataUsageListHelper.json1)

        mockServer.enqueue(mockedResponse1)
        mockServer.enqueue(mockedResponse2)

    }

    @After
    fun tearDown() = mockServer.shutdown()

    @Test
    fun getDataFromApi() = runBlockingTest {
        val data = repository.getMobileDataUsageFromApi().blockingLast()

        Truth.assertThat(data).isEqualTo(data)
    }

    @Test
    fun saveDataFromApi() = runBlockingTest {

        repository.getMobileDataUsage()

        val remoteData = repository.getMobileDataUsageFromApi().blockingLast()

        Truth.assertThat(remoteData).isEqualTo(data)

        val localData = dataDao.getData().blockingGet()

        data.id = 1 // increment id to 1 coz in Room, id is autoGenerate

        Truth.assertThat(localData).isEqualTo(data)

    }

}