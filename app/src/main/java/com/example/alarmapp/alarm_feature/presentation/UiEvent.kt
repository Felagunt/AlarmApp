package com.example.alarmapp.alarm_feature.presentation

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    data class Navigate(val route: String): UiEvent()
    object SaveAlarm: UiEvent()
}
