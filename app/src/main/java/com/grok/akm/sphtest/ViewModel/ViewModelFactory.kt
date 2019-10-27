package com.grok.akm.sphtest.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grok.akm.sphtest.repository.Repository
import javax.inject.Inject

class ViewModelFactory
@Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MobileDataUsageViewModel::class.java)) {
            return MobileDataUsageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}