package com.grok.akm.sphtest.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MobileDataUsageListTest {

    private val usage = MobileDataQuarterlyUsage(41.253494f,"2015-Q4",1)

    private val list = listOf(usage)
    private val offset = 0
    private val total = 50
    private val limit = 50

    @Mock
    lateinit var data: MobileDataUsageList

    @Before
    @Throws(Exception::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        Mockito.`when`(data.records).thenReturn(list)
        Mockito.`when`(data.offset).thenReturn(offset)
        Mockito.`when`(data.total).thenReturn(total)
        Mockito.`when`(data.limit).thenReturn(limit)
    }

    @Test
    fun testList() {
        Mockito.`when`(data.records).thenReturn(list)
        Assert.assertEquals(list, data.records)
    }

    @Test
    fun testLimit() {
        Mockito.`when`(data.limit).thenReturn(limit)
        Assert.assertEquals(50, data.limit)
    }

    @Test
    fun testTotal() {
        Mockito.`when`(data.total).thenReturn(total)
        Assert.assertEquals(50, data.total)
    }


    @Test
    fun testOffset() {
        Mockito.`when`(data.offset).thenReturn(offset)
        Assert.assertEquals(0, data.offset)
    }

}