package com.grok.akm.sphtest.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MobileDataUsageTest {

    private val year = 2015
    private val volume = 41.253494f
    private val isDecreased = true


    @Mock
    lateinit var data: MobileDataUsage

    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        Mockito.`when`(data.year).thenReturn(year)
        Mockito.`when`(data.volume).thenReturn(volume)
        Mockito.`when`(data.isDecreased).thenReturn(isDecreased)
    }

    @Test
    fun testYear() {
        Mockito.`when`(data.year).thenReturn(year)
        Assert.assertEquals(2015, data.year)
    }

    @Test
    fun testVolume() {
        Mockito.`when`(data.volume).thenReturn(volume)
        Assert.assertEquals(41.253494f, data.volume)
    }

    @Test
    fun testisDecreased() {
        Mockito.`when`(data.isDecreased).thenReturn(isDecreased)
        Assert.assertEquals(true, data.isDecreased)
    }


}
