package com.example.alarmapp.core.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alarmapp.alarm_feature.presentation.addEditAlarm.AddEditViewModel
import com.example.alarmapp.alarm_feature.presentation.addEditAlarm.components.AddEditAlarmScreen
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.ListOfAlarmsViewModel
import com.example.alarmapp.alarm_feature.presentation.listOfAlarms.components.LisOfAlarmsScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.ListOfAlarmsScreen.route
    ) {
        composable(ScreenRoutes.ListOfAlarmsScreen.route) {
            val  viewModel = hiltViewModel<ListOfAlarmsViewModel>()
            val state = viewModel.state
            val uiEvent = viewModel.uiEvent
            LisOfAlarmsScreen(
                state = state,
                nextAlarmTime = viewModel.nextAlarmTime,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
            ) {
                navController.navigate(ScreenRoutes.AddEditAlarmScreen.route)
            }
        }
        composable(
            route = ScreenRoutes.AddEditAlarmScreen.route + "?alarmId={alarmId}",
            arguments = listOf(
                navArgument(
                    name = "alarmId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )

        ) {
            val viewModel = hiltViewModel<AddEditViewModel>()
            val state = viewModel.state
            val alarmEvent = viewModel.alarmEvent
            AddEditAlarmScreen(
                alarmEvent = alarmEvent,
                state = state,
                onEvent = viewModel::onEvent,
            ) {
                navController.navigate(ScreenRoutes.AddEditAlarmScreen.route)
            }
            //it.arguments?.getInt("alarmId")

        }
    }
}