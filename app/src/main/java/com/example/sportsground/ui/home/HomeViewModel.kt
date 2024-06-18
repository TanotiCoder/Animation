package com.example.sportsground.ui.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sportsground.R
import com.example.sportsground.data.GroundModel
import com.example.sportsground.data.ResultResponse
import com.example.sportsground.data.repository.GroundRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID


data class ErrorMessage(val id: Long, @StringRes val messageId: Int)

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>


    data class NoGround(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState

    data class HasGround(
        val groundFeed: List<GroundModel>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState
}


private data class HomeViewModelState(
    val groundFeed: List<GroundModel>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
) {
    fun toUiState(): HomeUiState = if (groundFeed == null) {
        HomeUiState.NoGround(
            isLoading = isLoading, errorMessages = errorMessages
        )
    } else {
        HomeUiState.HasGround(
            isLoading = isLoading, groundFeed = groundFeed, errorMessages = errorMessages
        )
    }
}

class HomeViewModel(private val groundRepository: GroundRepository) : ViewModel() {
    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true
        )
    )

    val uiState = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        getGroundData()
    }

    fun getGroundData() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = groundRepository.getAllGround()
            viewModelState.update {
                when (result) {
                    is ResultResponse.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }

                    is ResultResponse.Success -> {
                        it.copy(groundFeed = result.data, isLoading = false)
                    }
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            groundRepository: GroundRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(groundRepository) as T
            }
        }
    }
}