package com.example.alarmapp.alarm_feature.presentation.listOfAlarms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alarmapp.alarm_feature.domain.model.Alarm

@Composable
fun AlarmListItem(
    alarm: Alarm,
    modifier: Modifier = Modifier,
    OnItemClick: (Alarm) -> Unit,
    OnDeleteAlarmClick: () -> Unit,
    isEnabled: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { OnItemClick(alarm) }
            .clip(RoundedCornerShape(8.dp))
            .border(4.dp, Color.DarkGray)
            .shadow(
                15.dp,
                RoundedCornerShape(8.dp),
                ambientColor = MaterialTheme.colors.onSecondary
            )
            .background(MaterialTheme.colors.onSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alarm.ringsTime.toString(),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary
            )
            Checkbox(checked = false, onCheckedChange ={ isEnabled()})
            IconButton(onClick = OnDeleteAlarmClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete alarm",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}