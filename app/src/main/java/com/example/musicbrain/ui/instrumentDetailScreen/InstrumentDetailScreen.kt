package com.example.musicbrain.ui.instrumentDetailScreen

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
import androidx.compose.material3.MaterialTheme
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
import com.example.musicbrain.model.Instrument
import com.example.musicbrain.ui.components.DetailImage
import com.example.musicbrain.ui.components.InfoRow

@Composable
fun InstrumentDetailScreen(
    instrumentId: String,
    onBack: () -> Unit,
    detailViewModel: InstrumentDetailViewModel =
        viewModel(
            factory = InstrumentDetailViewModel.Factory,
        ),
) {
    LaunchedEffect(instrumentId) {
        detailViewModel.getApiInstrument(instrumentId = instrumentId)
    }

    val instrument by detailViewModel.instrument.collectAsState()
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
        when (detailViewModel.instrumentApiState) {
            InstrumentApiState.Success ->
                instrument?.let {
                    InstrumentDetail(
                        instrument = it,
                    )
                }
            InstrumentApiState.NotFound -> Text("Instrument not found")
            InstrumentApiState.Error -> Text("Error")
            InstrumentApiState.Loading -> Text("Loading")
        }
    }
}

@Composable
fun InstrumentDetail(
    instrument: Instrument,
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
        DetailImage(
            id = R.drawable.instrumentsicon,
            contentDescription = "Instrument Icon",
        )
        Text(
            instrument.name,
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
        Text(
            instrument.description,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
        )
        Divider()

        InfoRow(
            field = stringResource(id = R.string.type),
            value = instrument.type,
        )
        Divider()
        InfoRow(
            field = stringResource(id = R.string.score),
            value = instrument.score.toString(),
        )
    }
}
