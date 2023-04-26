package com.example.alarmapp.core.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alarmapp.alarm_feature.presentation.addEditAlarm.components.AddEditAlarmScreen
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.components.LisOfAlarmsScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.ListOfAlarmsScreen.route
    ) {
        composable(ScreenRoutes.ListOfAlarmsScreen.route) {
            LisOfAlarmsScreen(navController = navController)
        }
        composable(
            route = ScreenRoutes.AddEditAlarmScreen.route + "/{alarmId}",
            arguments = listOf(
                navArgument(
                    name = "alarmId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )

        ) {
            AddEditAlarmScreen(
                navController = navController
            )
        }
    }
}