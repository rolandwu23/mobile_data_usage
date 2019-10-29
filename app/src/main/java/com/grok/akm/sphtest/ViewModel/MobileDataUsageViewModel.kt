package com.grok.akm.sphtest.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grok.akm.sphtest.model.*
import com.grok.akm.sphtest.repository.Repository
import com.grok.akm.sphtest.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MobileDataUsageViewModel (private val repository: Repository) : ViewModel() {

    private var mobileDataUsage: MutableLiveData<ApiResponse> = MutableLiveData()
    private var compositeDisposable = CompositeDisposable()

    fun loadMobileDataUsage() = viewModelScope.launch(Dispatchers.IO) {
        compositeDisposable.add(repository.getMobileDataUsage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mobileDataUsage.postValue(dataLoading()) }
            .subscribe(
                { result -> mobileDataUsage.postValue(dataSuccess(processDataResponse(result))) },
                { throwable -> mobileDataUsage.postValue(dataError(throwable)) }
            ))
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

    fun getMobileDataUsageLiveData(): MutableLiveData<ApiResponse> {
        return mobileDataUsage
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}