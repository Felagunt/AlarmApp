package com.example.alarmapp.alarmManager_feature

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("Extra_message")
    }
}