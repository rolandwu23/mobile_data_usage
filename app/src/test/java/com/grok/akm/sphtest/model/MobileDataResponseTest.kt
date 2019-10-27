package com.grok.akm.sphtest.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MobileDataResponseTest {

    private val usage = MobileDataQuarterlyUsage(41.253494f,"2015-Q4",1)

    private val list = MobileDataUsageList(listOf(usage),0,10,10)
    private val id = 1
    private val success = true

    @Mock
    lateinit var data: MobileDataResponse

    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        Mockito.`when`(data.id).thenReturn(id)
        Mockito.`when`(data.success).thenReturn(success)
        Mockito.`when`(data.result).thenReturn(list)

    }

    @Test
    fun testId() {
        Mockito.`when`(data.id).thenReturn(id)
        Assert.assertEquals(1, data.id)
    }

    @Test
    fun testSuccess() {
        Mockito.`when`(data.success).thenReturn(success)
        Assert.assertEquals(true, data.success)
    }

    @Test
    fun testList() {
        Mockito.`when`(data.result).thenReturn(list)
        Assert.assertEquals(list, data.result)
    }


}