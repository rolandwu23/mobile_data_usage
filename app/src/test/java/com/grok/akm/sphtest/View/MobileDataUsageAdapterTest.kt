package com.grok.akm.sphtest.View

import android.content.Context
import android.os.Build
import android.view.View
import androidx.cardview.widget.CardView
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.grok.akm.sphtest.MobileDataUsageListHelper
import com.grok.akm.sphtest.model.MobileDataQuarterlyUsage
import com.grok.akm.sphtest.model.MobileDataResponse
import com.grok.akm.sphtest.model.MobileDataUsage
import com.grok.akm.sphtest.util.Util
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.ArrayList

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MobileDataUsageAdapterTest {

    @Mock
    private var context: Context? = null

    @Mock
    private var mobileDataUsageAdapter: MobileDataUsageAdapter? = null

    private var mobileDataResponse = Gson().fromJson(
        MobileDataUsageListHelper.json1,
        MobileDataResponse::class.java)

    private var mobileDataUsages: List<MobileDataUsage> = processDataResponse(mobileDataResponse)

    @Before
    @Throws(Exception::class)
    fun before() {
        context = ApplicationProvider.getApplicationContext()

        mobileDataUsageAdapter = MobileDataUsageAdapter(context!!,mobileDataUsages)
    }

    @Test
    @Throws(Exception::class)
    fun onBindViewHolder_shouldBindCorrectly() {
        // Item index : 2
        // Year 2010
        // volume 11.453191

        val cardView = CardView(context!!)
        val viewHolder = mobileDataUsageAdapter!!.onCreateViewHolder(cardView, 2)
        mobileDataUsageAdapter!!.onBindViewHolder(viewHolder, 2)


        val tvMobileDataUsageValue = viewHolder.tvMobileDataUsageValue
        val tvYearValue = viewHolder.tvYearValue
        val imgActionImage = viewHolder.imgActionImage


        // volume
        val val1 = java.lang.Float.valueOf(tvMobileDataUsageValue.text.toString())
        Truth.assertThat(val1).isEqualTo(11.453191f)
        // year
        Assert.assertEquals(tvYearValue.text.toString(), "2010")
        //image
        if (mobileDataUsages[2].isDecreased) {
            Assert.assertEquals(imgActionImage.visibility.toLong(), View.VISIBLE.toLong())
        } else {
            Assert.assertEquals(imgActionImage.visibility.toLong(), View.GONE.toLong())
        }
    }

    @Test
    fun getItemCount_shouldReturnsListSize() {
        Assert.assertEquals(mobileDataUsages.size, mobileDataUsageAdapter!!.itemCount)
    }


    private fun processDataResponse(dataResponse : MobileDataResponse) : List<MobileDataUsage> {
        val map = LinkedHashMap<Int, List<MobileDataQuarterlyUsage>>()
        val mobileDataUsageList: MutableList<MobileDataUsage> = mutableListOf()
        for (mobileDataQuarterlyUsage in dataResponse.result.records) {
            val year = Util.getYearFromString(
                mobileDataQuarterlyUsage.quarterStr
            )
            if (year in 2008..2019) {
                if (map.containsKey(year)) {
                    val list = map[year]?.toMutableList()
                    list?.add(mobileDataQuarterlyUsage)
                    if (list != null) map[year] = list
                } else {
                    val list = ArrayList<MobileDataQuarterlyUsage>()
                    list.add(mobileDataQuarterlyUsage)
                    map[year] = list
                }
            }
        }

        for ((key, value) in map) {
            val quarterFrom = Util.isDecreasedFrom(value)
            val quarterTo = quarterFrom + 1
            val quarterFromStr = "Q$quarterFrom"
            val quarterToStr = "Q$quarterTo"

            val q1 = Util.getQuarterlyUsage(value,1)
            val q2 = Util.getQuarterlyUsage(value,2)
            val q3 = Util.getQuarterlyUsage(value,3)
            val q4 = Util.getQuarterlyUsage(value,4)
            val mobileDataUsage = MobileDataUsage(key,
                Util.getVolume(value),
                Util.isDecreased(value),
                q1,q2,
                q3,q4,
                quarterFromStr,quarterToStr)
            mobileDataUsageList.add(mobileDataUsage)
        }
        return mobileDataUsageList
    }


}
