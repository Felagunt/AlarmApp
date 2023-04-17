package com.example.alarmapp.alarmManager_feature.data

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import com.example.alarmapp.alarmManager_feature.AlarmItem
import com.example.alarmapp.alarmManager_feature.AlarmReceiver
import com.example.alarmapp.alarmManager_feature.AlarmScheduler
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduler(item: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("Extra_message", item.message)//data send to
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        )
    }

    override fun cancel(item: AlarmItem) {
        TODO("Not yet implemented")
    }
}