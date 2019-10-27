package com.grok.akm.sphtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MobileDataQuarterlyUsage(
    @SerializedName("volume_of_mobile_data") @Expose var volume:Float,
    @SerializedName("quarter") @Expose var quarterStr:String,
    @SerializedName("_id") @Expose var id:Int
)