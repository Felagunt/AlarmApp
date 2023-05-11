//package com.example.alarmapp.alarmManager_feature
//
//import android.content.Context
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.alarmapp.alarmManager_feature.data.AndroidAlarmScheduler
//import java.time.LocalDateTime
//
//@Composable
//fun AlarmsFires(
//    context: Context
//) {
//    val scheduler = AndroidAlarmScheduler(context = context )
//    var alarmItem: AlarmItem? = null
//    var secondsText by remember {
//        mutableStateOf("")
//    }
//    var message by remember {
//        mutableStateOf("")
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        OutlinedTextField(
//            value = secondsText,
//            onValueChange = {
//                secondsText = it
//            },
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = {
//                Text(text = "Trigger alarm")
//            }
//        )
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Button(onClick = {
//                alarmItem?.let(scheduler::cancel)
//            }) {
//                Text(text = "Cancel")
//            }
//            Button(onClick = {
//                alarmItem = AlarmItem(
//                    time = LocalDateTime.now().plusSeconds(
//                        secondsText.toLong()
//                    ),
//                    message = message
//                )
//                alarmItem?.let(scheduler::scheduler)
//                secondsText = ""
//                message = ""
//            }) {
//                Text(text = "Wait 5 minutes")
//            }
//        }
//    }
//}