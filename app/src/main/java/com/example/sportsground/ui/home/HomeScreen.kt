package com.example.sportsground.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sportsground.data.GroundModel
import com.example.sportsground.ui.theme.SportsGroundTheme


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    setOnClickListener: (id: Int) -> Unit
) {
    when (uiState) {
        is HomeUiState.HasGround -> {
            HomeScreen(list = uiState.groundFeed, setOnClickListener = setOnClickListener)
        }

        is HomeUiState.NoGround -> {
            NoDataScreen()
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    list: List<GroundModel>,
    setOnClickListener: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list) { item ->
            HomeElement(groundModel = item, setOnClickListener = setOnClickListener)
        }
    }
}


@Composable
fun HomeElement(
    modifier: Modifier = Modifier,
    groundModel: GroundModel,
    setOnClickListener: (id: Int) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { setOnClickListener(groundModel.id) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = groundModel.name)
        }
    }
}

@Preview
@Composable
private fun TestElement() {
    SportsGroundTheme {
        HomeElement(groundModel = GroundModel(name = "Sai Dubey", id = 0)) {}
    }
}