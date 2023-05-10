package com.example.alarmapp.alarmManager_feature.presetation.finale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltViewModel
class FinaleViewModel(): ViewModel() {

    private val _currentTime = MutableStateFlow<String>("")
    val currentTime: StateFlow<String> = _currentTime

    private fun currentTime()  {
        viewModelScope.launch {
            val time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
            _currentTime.emit(time)
        }

    }
}