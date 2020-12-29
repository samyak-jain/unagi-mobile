package dev.samyak.unagi.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.samyak.core.data.Episode
import dev.samyak.core.data.Show
import dev.samyak.unagi.data.EpisodeRepo
import dev.samyak.unagi.data.ShowRepo
import kotlinx.coroutines.launch

class EpisodeScreenModel @ViewModelInject constructor(
    private val episodeRepo: EpisodeRepo,
    private val showRepo: ShowRepo
): ViewModel() {
    private val _episodeLiveData = MutableLiveData<List<Episode>>()
    val episodeLiveData: LiveData<List<Episode>>
        get() = _episodeLiveData

    private val _showLiveData = MutableLiveData<List<Show>>()
    val showLiveData: LiveData<List<Show>>
        get() = _showLiveData

    fun fetchEpisodes(showId: Int) = viewModelScope.launch {
        _episodeLiveData.postValue(episodeRepo.fetchAllEpisodes(showId))
    }

    fun startTranscoding(episodeId: Int) = viewModelScope.launch {
        episodeRepo.startTranscoding(episodeId)
    }

    fun getShow(showId: Int) = viewModelScope.launch {
        showRepo.getShow(showId)?.let {
            _showLiveData.postValue(listOf(it))
        }
    }
}