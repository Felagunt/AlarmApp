package com.example.alarmapp.alarm_feature.presentation.addEditAlarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@ExperimentalMaterialApi
@Composable
fun DropDownMenuComponent(params: DropDownMenuParameter) {
    var expanded by remember { mutableStateOf(params.expanded) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        BasicTextField(
            modifier = Modifier
                .background(params.backgroundColor)
                .fillMaxWidth(),
            readOnly = true,
            value = params.selectedOptionText,
            onValueChange = { },
            textStyle = TextStyle(
                color = Color.White,
                textAlign = TextAlign.End,
                fontSize = 16.sp,
            ),
            singleLine = true

        )
        ExposedDropdownMenu(
            modifier = Modifier
                .background(params.backgroundColor),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            params.options.forEach { selectionOption ->
                DropdownMenuItem(
                    modifier = Modifier
                        .background(params.backgroundColor),
                    onClick = {
                        params.selectedOptionText = selectionOption
                        expanded = false
                    },

                    ) {
                    Text(
                        text = selectionOption,
                        color = Color.White,
                    )

                }
            }
        }
    }

}