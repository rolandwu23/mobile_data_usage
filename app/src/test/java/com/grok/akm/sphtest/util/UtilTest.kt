package com.grok.akm.sphtest.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.grok.akm.sphtest.model.MobileDataQuarterlyUsage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    private val quarterlyUsage1 = MobileDataQuarterlyUsage(9.687363f,"2015-Q1",43)
    private val quarterlyUsage2 = MobileDataQuarterlyUsage(9.98677f,"2015-Q2",44)
    private val quarterlyUsage3 = MobileDataQuarterlyUsage(10.902194f,"2015-Q3",45)
    private val quarterlyUsage4 = MobileDataQuarterlyUsage(10.677166f,"2015-Q4",46)

    private val quarterlyUsageList = listOf(quarterlyUsage1,quarterlyUsage2,quarterlyUsage3,quarterlyUsage4)

    private val volume = 41.253493f

    @Test
    fun getVolumeTest() {

        val result = Util.getVolume(quarterlyUsageList)

        Truth.assertThat(result).isEqualTo(volume)
    }

    @Test
    fun isDecreasedTest() {

        val result = Util.isDecreased(quarterlyUsageList)

        Truth.assertThat(result).isTrue()

    }

    @Test
    fun getYearFromStringTest() {

        val result = Util.getYearFromString(quarterlyUsage1.quarterStr)

        Truth.assertThat(result).isEqualTo(2015)
    }

    @Test
    fun getQuarterFromStringTest() {

        val result = Util.getQuarterFromString(quarterlyUsage1.quarterStr)

        Truth.assertThat(result).isEqualTo(1)
    }

}