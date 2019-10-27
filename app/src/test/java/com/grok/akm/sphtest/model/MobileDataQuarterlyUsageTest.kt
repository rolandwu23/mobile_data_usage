package com.grok.akm.sphtest.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MobileDataQuarterlyUsageTest {

    private val quarter = "2015-Q4"
    private val volume = 41.253494f
    private val id = 1


    @Mock
    lateinit var data: MobileDataQuarterlyUsage

    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        Mockito.`when`(data.quarterStr).thenReturn(quarter)
        Mockito.`when`(data.volume).thenReturn(volume)
        Mockito.`when`(data.id).thenReturn(id)
    }

    @Test
    fun testYear() {
        Mockito.`when`(data.quarterStr).thenReturn(quarter)
        Assert.assertEquals("2015-Q4", data.quarterStr)
    }

    @Test
    fun testVolume() {
        Mockito.`when`(data.volume).thenReturn(volume)
        Assert.assertEquals(41.253494f, data.volume)
    }

    @Test
    fun testId() {
        Mockito.`when`(data.id).thenReturn(id)
        Assert.assertEquals(1, data.id)
    }


}