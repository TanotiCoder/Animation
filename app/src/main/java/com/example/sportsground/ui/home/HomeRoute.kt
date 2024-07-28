package com.example.sportsground.ui.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    setOnClickListener: (name: String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    HomeRoute(uiState = uiState, setOnClickListener = setOnClickListener)
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    setOnClickListener: (name: String) -> Unit
) {
    val homeListLazyListState = rememberLazyListState()
    val groundLazyListState = when (uiState) {
        is HomeUiState.HasGround -> uiState.groundFeed
        is HomeUiState.NoGround -> emptyList()
    }.associate { groundModel ->
        key(groundModel.id) {
            groundModel.id to rememberLazyListState()
        }
    }
    HomeScreen(uiState = uiState, setOnClickListener = setOnClickListener)
}