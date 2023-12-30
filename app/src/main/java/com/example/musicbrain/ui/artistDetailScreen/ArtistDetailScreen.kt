package com.example.musicbrain.ui.artistDetailScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicbrain.model.Artist

@Composable
fun ArtistDetailScreen(
    artistId: String,
    onBack: () -> Unit,
    detailViewModel: ArtistDetailViewModel = viewModel(
        factory = ArtistDetailViewModel.Factory,
    ),
) {

    LaunchedEffect(artistId) {
        detailViewModel.getApiArtist(artistId = artistId)
    }

    val artist by detailViewModel.artist.collectAsState()
    Column {
        Row {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable {
                        onBack()
                    }
            )
        }
        when (detailViewModel.artistApiState) {
            ArtistApiState.Success -> artist?.let {
                ArtistDetail(
                    artist = it,
                    modifier = Modifier.fillMaxSize())
            }
            ArtistApiState.NotFound -> Text("Artist not found")
            ArtistApiState.Error -> Text("Error")
            ArtistApiState.Loading -> Text("Loading")
        }
    }
}

@Composable
fun ArtistDetail(
    artist: Artist,
    modifier: Modifier = Modifier
) {
    Log.d("ArtistDetail", "ArtistDetail.type: ${artist.type}")
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(artist.type) {
            "Person" -> {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person",
//                    modifier = modifier
                )
            }
            "Group" -> {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = "Group",
//                    modifier = modifier
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Default.QuestionMark,
                    contentDescription = "Unknown",
//                    modifier = modifier
                )
            }
        }
        Text(artist.name)
        Text(text = artist.disambiguation)
        Text(text = artist.gender)
        Text(text = artist.score.toString())

    }
}