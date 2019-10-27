package com.grok.akm.sphtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MobileDataUsageList(
    @SerializedName("records") @Expose var records:List<MobileDataQuarterlyUsage>,
    @SerializedName("offset") @Expose var offset:Int,
    @SerializedName("limit") @Expose var limit:Int,
    @SerializedName("total") @Expose var total:Int
)