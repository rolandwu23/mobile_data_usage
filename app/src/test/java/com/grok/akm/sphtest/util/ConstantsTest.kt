package com.grok.akm.sphtest.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConstantsTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @Test
    fun tagTest(){

        Truth.assertThat(Constants.TAG).isEqualTo("sptech_android_app")

    }

    @Test
    fun pageLimitTest() {

        Truth.assertThat(Constants.PAGE_LIMIT).isEqualTo(10)
    }

    @Test
    fun apiResourceIdTest() {

        Truth.assertThat(Constants.API_RESOURCE_ID).isEqualTo("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
    }

    @Test
    fun urlTest() {

        Truth.assertThat(Constants.DATA_GOV_SG_URL).isEqualTo("https://data.gov.sg")
    }
}