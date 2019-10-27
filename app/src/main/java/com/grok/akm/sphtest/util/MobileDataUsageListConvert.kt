package com.grok.akm.sphtest.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.grok.akm.sphtest.model.MobileDataUsageList

class MobileDataUsageListConvert {

    var gson = Gson()

    @TypeConverter
    fun stringToMobileDataUsageList(data: String?): MobileDataUsageList {

        val listType = object : TypeToken<MobileDataUsageList>() {

        }.getType()

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun mobileDataUsageListToString(list: MobileDataUsageList): String {
        return gson.toJson(list)
    }

}