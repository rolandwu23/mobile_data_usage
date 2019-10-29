package com.grok.akm.sphtest.model

data class MobileDataUsage(var year:Int,
                           var volume:Float,
                           var isDecreased:Boolean,
                           var q1:Float,
                           var q2:Float,
                           var q3:Float,
                           var q4:Float,
                           var from:String,
                           var to:String)