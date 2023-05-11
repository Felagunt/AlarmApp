package com.example.alarmapp.core.presentation

sealed class ScreenRoutes(val route: String) {
    object ListOfAlarmsScreen: ScreenRoutes("list_of_alarms_screen")
    object AddEditAlarmScreen: ScreenRoutes("add_edit_alarm_screen")
    object FireAlarmsScreen: ScreenRoutes("fire_alarms_screen")
    object FinaleScreen: ScreenRoutes("finale_screen")
}
