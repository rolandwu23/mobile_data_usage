package com.grok.akm.sphtest.db

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.grok.akm.sphtest.MainCoroutineRule
import com.grok.akm.sphtest.model.MobileDataQuarterlyUsage
import com.grok.akm.sphtest.model.MobileDataResponse
import com.grok.akm.sphtest.model.MobileDataUsageList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@SmallTest
class DataDaoTest {

    private lateinit var database: DataDB

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataDB::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertDataAndGetData() = runBlockingTest {
        // GIVEN - insert a task
        val quarterlyUsage = MobileDataQuarterlyUsage(41.34566f,"2015_q3",12)
        val usageList = MobileDataUsageList(listOf(quarterlyUsage),12,34,34)
        val data = MobileDataResponse(1, true,usageList)

        database.dataDao().setData(data)

        // WHEN - Get the task by id from the database
        val loaded = database.dataDao().getData()
        val response : MobileDataResponse? = loaded.blockingGet()

        // THEN - The loaded data contains the expected values
        MatcherAssert.assertThat<MobileDataResponse>(response as MobileDataResponse, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(response.id, CoreMatchers.`is`(data.id))
        MatcherAssert.assertThat(response.result, CoreMatchers.`is`(data.result))
        MatcherAssert.assertThat(response.success, CoreMatchers.`is`(data.success))
    }



    @Test
    fun deleteTasksAndGettingTasks() = runBlockingTest {

        val quarterlyUsage = MobileDataQuarterlyUsage(41.34566f,"2015_q3",12)
        val usageList = MobileDataUsageList(listOf(quarterlyUsage),12,34,34)
        val data = MobileDataResponse(1, true,usageList)

        database.dataDao().setData(data)

        // When deleting all tasks
        database.dataDao().deleteAll()

        // THEN - The list is empty
        val dataList = database.dataDao().getData()
        val response = dataList.blockingGet()
        MatcherAssert.assertThat(response==null, CoreMatchers.`is`(true))
    }


}