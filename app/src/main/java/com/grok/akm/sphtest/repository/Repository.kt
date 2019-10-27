package com.grok.akm.sphtest.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.grok.akm.sphtest.api.ApiCallInterface
import com.grok.akm.sphtest.db.DataDao
import com.grok.akm.sphtest.model.MobileDataResponse
import com.grok.akm.sphtest.util.Constants
import io.reactivex.Observable
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

class Repository
@Inject constructor(private val apiCallInterface: ApiCallInterface,
                    private val dataDao: DataDao
){

    suspend fun getMobileDataUsage() : Observable<MobileDataResponse> {
        val dataFromApi = getMobileDataUsageFromApi()
        val dataFromDB = getMobileDataUsageFromDB()
        return if(hasInternetConnection()){
            Observable.concatArrayEager(dataFromApi, dataFromDB)
        }else {
            dataFromDB
        }
    }


    fun getMobileDataUsageFromApi() : Observable<MobileDataResponse> {
        return apiCallInterface.getMobileDataUsage(Constants.API_RESOURCE_ID, Constants.PAGE_LIMIT)
            .flatMap {it1 ->

                val total = it1.result.total
                apiCallInterface.getMobileDataUsage(Constants.API_RESOURCE_ID,total)
                    .doOnNext { it2 ->
                        dataDao.setData(it2)
                    }
            }
    }

    fun getMobileDataUsageFromDB() : Observable<MobileDataResponse> {
        return dataDao.getData()
            .toObservable()
            .doOnNext {
                //Print log it.size :)
                Log.e("REPOSITORY DB *** ", it.toString())
            }

    }


    @WorkerThread
    private suspend fun hasInternetConnection(): Boolean {
        return try {
            val timeoutMs = 800
            val socket = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)

            socket.connect(socketAddress, timeoutMs)
            socket.close()
            true
        } catch (e: IOException) {
            false
        }
    }

    /* TODO For emitting data in offline mode, follow NetworkBoundResource approach in architecture-component-samples.
    link: https://github.com/android/architecture-components-samples/tree/master/GithubBrowserSample
     */
}
