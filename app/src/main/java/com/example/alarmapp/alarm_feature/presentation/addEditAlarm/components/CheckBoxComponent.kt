package com.example.alarmapp.alarm_feature.presentation.addEditAlarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.alarmapp.alarm_feature.domain.model.Alarm

@Composable
fun CheckBoxComponent(
    modifier: Modifier = Modifier,
    textLabel: String,
    checked: Boolean,
    onCheckChange:((Boolean) -> Unit)?
) {

    Box(
        modifier = modifier
            .size(50.dp)
            .shadow(15.dp, CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colors.onSecondary)
            .border(
                width = 3.dp,
                color = MaterialTheme.colors.secondaryVariant,
                shape = CircleShape
            )
    ) {
        Text(
            textLabel,
            style = MaterialTheme.typography.h4
        )
        if (onCheckChange != null) {
            Checkbox(
                checked = checked,
                onCheckedChange ={
                    onCheckChange(it)
                }
            )
        }
    }
}