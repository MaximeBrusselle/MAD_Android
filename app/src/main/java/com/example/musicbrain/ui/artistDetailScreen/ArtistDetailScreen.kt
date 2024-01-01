package com.example.musicbrain.ui.artistDetailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicbrain.R
import com.example.musicbrain.model.Artist
import com.example.musicbrain.ui.components.DetailImage
import com.example.musicbrain.ui.components.InfoRow

@Composable
fun ArtistDetailScreen(
    artistId: String,
    onBack: () -> Unit,
    detailViewModel: ArtistDetailViewModel =
        viewModel(
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
                modifier =
                    Modifier
                        .clickable {
                            onBack()
                        }
                        .padding(8.dp),
            )
        }
        when (detailViewModel.artistApiState) {
            ArtistApiState.Success ->
                artist?.let {
                    ArtistDetail(
                        artist = it,
                    )
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .padding(8.dp)
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (artist.type) {
            "Person" -> {
                DetailImage(
                    id = R.drawable.personicon,
                    contentDescription = artist.type,
                )
            }
            "Group" -> {
                DetailImage(
                    id = R.drawable.groupicon,
                    contentDescription = artist.type,
                )
            }
            else -> {
                DetailImage(
                    id = R.drawable.unknownicon,
                    contentDescription = artist.type,
                )
            }
        }

        Text(
            artist.name,
            style =
                TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    lineHeight = 36.sp,
                    letterSpacing = 0.sp,
                ),
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
        )
        Divider()

        InfoRow(
            field = stringResource(id = R.string.disambiguation),
            value = artist.disambiguation,
        )
        Divider()
        InfoRow(
            field = stringResource(id = R.string.gender),
            value = artist.gender,
        )
        Divider()
        InfoRow(
            field = stringResource(id = R.string.score),
            value = artist.score.toString(),
        )
    }
}
