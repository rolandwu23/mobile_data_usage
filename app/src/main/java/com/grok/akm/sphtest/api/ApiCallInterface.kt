package com.grok.akm.sphtest.api

import com.grok.akm.sphtest.model.MobileDataResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallInterface {

    @GET("/api/action/datastore_search")
    fun getMobileDataUsage(
        @Query("resource_id") id: String,
        @Query("limit") limit:Int
    ) : Observable<MobileDataResponse>

}