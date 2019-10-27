package com.grok.akm.sphtest.model

import com.grok.akm.sphtest.api.Status

data class ApiResponse(var status: Status?, var data: List<MobileDataUsage>?, var error:Throwable?)

fun dataLoading() = ApiResponse(Status.LOADING,null,null)

fun dataSuccess(data: List<MobileDataUsage>) = ApiResponse(Status.SUCCESS,data,null)

fun dataError(error: Throwable) = ApiResponse(Status.ERROR,null,error)