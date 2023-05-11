package com.example.alarmapp.alarmManager_feature.data

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.alarmapp.MainActivity
import com.example.alarmapp.alarmManager_feature.AlarmItem
import com.example.alarmapp.alarmManager_feature.AlarmReceiver
import com.example.alarmapp.alarmManager_feature.AlarmScheduler
import com.example.alarmapp.alarm_feature.domain.model.Alarm
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("MissingPermission")
    override fun scheduler(item: Alarm) {
//        val int = Intent(context, MainActivity::class.java).apply {
//            putExtra("alarmId", item.alarmId)
//        }
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("Extra_message", item.ringMelody)//data send to
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.ringsTime.toEpochSecond() * 1000,//.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),//item.id.hashCode()
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}