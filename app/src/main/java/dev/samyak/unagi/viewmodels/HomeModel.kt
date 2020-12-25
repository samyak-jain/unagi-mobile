package dev.samyak.unagi.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.samyak.core.data.Library
import dev.samyak.core.network.Failure
import dev.samyak.core.network.Success
import dev.samyak.unagi.data.LibraryRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HomeModel @ViewModelInject constructor(private val libraryRepo: LibraryRepo): ViewModel() {
    private val _libraryLiveData = MutableLiveData<List<Library>>()
    val libraryLiveData: LiveData<List<Library>>
        get() = _libraryLiveData

    init {
        fetchLibrary()
    }

    private fun fetchLibrary() = viewModelScope.launch {
        when (val results = libraryRepo.fetchAllLibrary()) {
            is Success -> {
                if (results.data.filterNotNull().isNotEmpty()) {
                    _libraryLiveData.value = results.data.filterNotNull()
                }
            }
            is Failure -> {

            }
        }
    }
}