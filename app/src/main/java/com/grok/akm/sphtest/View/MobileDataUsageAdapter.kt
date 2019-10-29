package com.grok.akm.sphtest.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.grok.akm.sphtest.R
import com.grok.akm.sphtest.model.MobileDataUsage

class MobileDataUsageAdapter (private val context: Context, private val mobileDataUsageList:List<MobileDataUsage>?) : RecyclerView.Adapter<MobileDataUsageAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.mobile_data_usage_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        if(mobileDataUsageList != null){
            return mobileDataUsageList.size
        }else{
            return 0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mobileDataUsage = mobileDataUsageList?.get(position)

        val year = mobileDataUsage?.year.toString()
        val volume = mobileDataUsage?.volume.toString()
        val quarterFrom = mobileDataUsage?.from
        val quarterTo = mobileDataUsage?.to
        val q1 = mobileDataUsage?.q1.toString()
        val q2 = mobileDataUsage?.q2.toString()
        val q3 = mobileDataUsage?.q3.toString()
        val q4 = mobileDataUsage?.q4.toString()

        holder.tvYearValue.text = year
        holder.tvMobileDataUsageValue.text = volume
        if (mobileDataUsage?.isDecreased!!) {
            holder.imgActionImage.visibility = View.VISIBLE
            val message = "Mobile Data Usage of year $year \n" +
                    "\t\t\t\t\t Q1:\t\t $q1 \n" +
                    "\t\t\t\t\t Q2:\t\t $q2 \n" +
                    "\t\t\t\t\t Q3:\t\t $q3 \n" +
                    "\t\t\t\t\t Q4:\t\t $q4 \n" +
                    "Usage decreased from $quarterFrom to $quarterTo"
            holder.imgActionImage.setOnClickListener { Toast.makeText(context,message, Toast.LENGTH_LONG).show() }
        } else {
            holder.imgActionImage.visibility = View.GONE
        }

    }


    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        var tvYearValue : TextView = root.findViewById<View>(R.id.tv_year_value) as TextView
        var tvMobileDataUsageValue : TextView = root.findViewById<View>(R.id.tv_mobile_data_usage_value) as TextView
        var imgActionImage : ImageView = root.findViewById<View>(R.id.action_image) as ImageView
    }

}