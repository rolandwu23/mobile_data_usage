package com.grok.akm.sphtest.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.grok.akm.sphtest.R
import com.grok.akm.sphtest.ViewModel.MobileDataUsageViewModel
import com.grok.akm.sphtest.ViewModel.ViewModelFactory
import com.grok.akm.sphtest.api.Status
import com.grok.akm.sphtest.di.MyApplication
import com.grok.akm.sphtest.model.ApiResponse
import com.grok.akm.sphtest.model.MobileDataUsage
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var mobileDataUsageViewModel: MobileDataUsageViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MyApplication).appComponent!!.doInjection(this)

        val layoutManager = LinearLayoutManager(this)
        activity_main_rv.layoutManager = layoutManager

        mobileDataUsageViewModel = ViewModelProviders.of(this,viewModelFactory).get(
            MobileDataUsageViewModel::class.java)

        mobileDataUsageViewModel?.getMobileDataUsageLiveData()?.observe(this, Observer<ApiResponse> { consumeApiResponse(it) })

        mobileDataUsageViewModel?.loadMobileDataUsage()

    }

    private fun consumeApiResponse(apiResponse: ApiResponse) {
        when (apiResponse.status) {

            Status.LOADING -> {
                shimmer_view_container.visibility = View.VISIBLE
                shimmer_view_container.startShimmer()
            }

            Status.SUCCESS -> renderSuccessResponse(apiResponse.data)

            Status.ERROR -> {
                shimmer_view_container.stopShimmer()
                shimmer_view_container.visibility = View.GONE
                Toast.makeText(this, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun renderSuccessResponse(list : List<MobileDataUsage>?){
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
        val adapter = MobileDataUsageAdapter(this,list)
        activity_main_rv.adapter = adapter
    }


}
