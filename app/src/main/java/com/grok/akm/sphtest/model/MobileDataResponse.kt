package com.grok.akm.sphtest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.grok.akm.sphtest.util.MobileDataUsageListConvert

@Entity(tableName = "MobileDataResponse")
@TypeConverters(MobileDataUsageListConvert::class)
data class MobileDataResponse(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "success")
    @SerializedName("success") @Expose var success:Boolean,

    @ColumnInfo(name = "result")
    @SerializedName("result") @Expose var result:MobileDataUsageList
)