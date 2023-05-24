package com.example.alarmapp.alarm_feature.presentation.addEditAlarm.components

import androidx.compose.ui.graphics.Color
import com.example.alarmapp.alarm_feature.domain.model.Melody

data class DropDownMenuParameter(
    var options: List<Melody>,
    var expanded: Boolean,
    var selectedOptionText: String,
    var backgroundColor: Color
)

