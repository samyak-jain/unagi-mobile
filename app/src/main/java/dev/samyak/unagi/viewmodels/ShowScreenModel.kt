package dev.samyak.unagi.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.samyak.core.data.Show
import dev.samyak.unagi.data.LibraryRepo
import dev.samyak.unagi.data.ShowRepo
import kotlinx.coroutines.launch

class ShowScreenModel @ViewModelInject constructor(
        private val showRepo: ShowRepo,
        private val libraryRepo: LibraryRepo
    ): ViewModel() {
    private val _showLiveData = MutableLiveData<List<Show>>()
    val showLiveData: LiveData<List<Show>>
        get() = _showLiveData

    private val _libraryName = MutableLiveData<String>()
    val libraryName: LiveData<String>
        get() = _libraryName

    fun fetchShows(libraryId: Int) = viewModelScope.launch {
        _showLiveData.postValue(showRepo.fetchAllShows(libraryId))
    }

    fun getLibrary(libraryId: Int) = viewModelScope.launch {
        _libraryName.postValue(libraryRepo.getLibrary(libraryId)?.name)
    }
}