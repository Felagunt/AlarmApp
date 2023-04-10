package com.example.alarmapp.alarm_feature.presentation

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    object SaveAlarm: UiEvent()
}
