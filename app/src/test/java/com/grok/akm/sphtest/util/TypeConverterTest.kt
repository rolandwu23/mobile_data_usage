package com.grok.akm.sphtest.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.grok.akm.sphtest.MobileDataUsageListHelper
import com.grok.akm.sphtest.model.MobileDataQuarterlyUsage
import com.grok.akm.sphtest.model.MobileDataUsageList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TypeConverterTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    private val quarterlyUsage = MobileDataQuarterlyUsage(0.003323f,"2006-Q4",1)
    private val dataList = MobileDataUsageList(listOf(quarterlyUsage),0,1,59)


    @Test
    fun stringToListTest() {

        val result = MobileDataUsageListConvert().stringToMobileDataUsageList(
            MobileDataUsageListHelper.jsonTest)

        Truth.assertThat(result).isEqualTo(dataList)
    }

    @Test
    fun listToStringTest() {

        val result = MobileDataUsageListConvert().mobileDataUsageListToString(dataList)

        Truth.assertThat(result).isEqualTo(MobileDataUsageListHelper.jsonTest)
    }

}