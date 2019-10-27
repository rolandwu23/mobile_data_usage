package com.grok.akm.sphtest.util

import android.util.Log
import com.grok.akm.sphtest.model.MobileDataQuarterlyUsage

object Util {

    /**
     * Returns volume total
     * @param list a list of MobileDataQuarterlyUsage
     * @return volume total
     */
    fun getVolume(list: List<MobileDataQuarterlyUsage>?): Float {
        var result = 0f
        if (list != null && list.isNotEmpty()) {
            for (mobileDataQuarterlyUsage in list) {
                result += mobileDataQuarterlyUsage.volume
            }
        }
        return result
    }

    /**
     * Returns whether mobile data usage is decreased in subsequent quarter
     * @param list a list of MobileDataQuarterlyUsage
     * @return mobile data usage is decreased or not
     */
    fun isDecreased(list: List<MobileDataQuarterlyUsage>?): Boolean {
        var isDecreased = false
        if (list != null && list.isNotEmpty()) {
            var volume = 0f
            for (mobileDataQuarterlyUsage in list) {
                if (mobileDataQuarterlyUsage.volume < volume) {
                    isDecreased = true
                    break
                }
                volume = mobileDataQuarterlyUsage.volume
            }
        }
        return isDecreased
    }

    /**
     * Returns quarter number when mobile data usage is decreased in subsequent quarter
     * @param list a list of MobileDataQuarterlyUsage
     * @return quarter number
     */
    fun isDecreasedFrom(list: List<MobileDataQuarterlyUsage>?): Int {
        var isDecreased = -1
        if (list != null && list.isNotEmpty()) {
            var volume = 0f
            var quarterStrFrom = ""
            for (mobileDataQuarterlyUsage in list) {
                if (mobileDataQuarterlyUsage.volume < volume) {
                    isDecreased = getQuarterFromString(quarterStrFrom)
                    break
                }
                volume = mobileDataQuarterlyUsage.volume
                quarterStrFrom = mobileDataQuarterlyUsage.quarterStr
            }
        }
        return isDecreased
    }

    /**
     * Returns year from a string which contains quarter and year
     * @param string contains quarter and year
     * @return year
     */
    fun getYearFromString(string: String?): Int {
        var result = 0
        if (string != null) {
            val array = string.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            try {
                result = Integer.valueOf(array[0])
            } catch (e: NumberFormatException) {
                Log.d(Constants.TAG, "-- getYearFromString " + e.message)
            }

        }
        return result
    }

    fun getQuarterFromString(string: String?): Int {
        var result = 0
        if (string != null) {
            val array = string.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            try {
                result = Integer.valueOf(array[1].substring(1))
            } catch (e: NumberFormatException) {
                Log.d(Constants.TAG, "-- getYearFromString " + e.message)
            }

        }
        return result
    }


}