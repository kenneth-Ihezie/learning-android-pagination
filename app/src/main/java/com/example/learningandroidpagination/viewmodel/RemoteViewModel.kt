package com.example.learningandroidpagination.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.learningandroidpagination.model.DoggoImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DoggoImagesRepository.getInstance()

    fun fetchDoggoImages(): Flow<PagingData<String>>{
        return repository.letDoggoImagesFlow()
            .map { it -> it.map { it.url } }
            .cachedIn(viewModelScope)
    }
}