package com.example.alarmapp.alarm_feature.presentation.addEditAlarm.components

import androidx.compose.ui.graphics.Color

data class DropDownMenuParameter(
    var options: List<String>,
    var expanded: Boolean,
    var selectedOptionText: String,
    var backgroundColor: Color
)

