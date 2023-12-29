package com.example.musicbrain.ui.artistScreen

import androidx.work.WorkInfo
import com.example.musicbrain.model.Artist

data class ArtistsState(
    val query: String = "",
    val active: Boolean = false,
)

data class ArtistListState(val artists: List<Artist> = listOf())

data class WorkerState(val workerInfo: WorkInfo? = null)

sealed interface ArtistApiState {
    object Success : ArtistApiState
    object NotFound : ArtistApiState
    object Error : ArtistApiState
    object Loading : ArtistApiState
}