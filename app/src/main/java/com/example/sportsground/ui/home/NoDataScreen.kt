package com.example.sportsground.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sportsground.R

@Composable
fun NoDataScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.baseline_hourglass_empty_24),
                contentDescription = "Empty",
                contentScale = ContentScale.Crop
            )
            Text(text = "No Data Found", textAlign = TextAlign.Center)
        }
    }
}