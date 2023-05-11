package com.example.alarmapp.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alarmapp.MainActivity
import com.example.alarmapp.alarmManager_feature.presetation.FinaleScreen
import com.example.alarmapp.alarmManager_feature.presetation.fires_alarms.FireAlarmsScreen
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
            LisOfAlarmsScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
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
            AddEditAlarmScreen(
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = ScreenRoutes.FireAlarmsScreen.route + "?") {
            val context = LocalContext.current
            FireAlarmsScreen(
                context = context,
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(route = ScreenRoutes.FinaleScreen.route) {
            FinaleScreen(
                onPopBackStack = {
                    if(navController.popBackStack()) {
                        MainActivity::finish
                    }
                }
            )
        }
    }
}
//
composable(
"details?article={argument}",
deepLinks = listOf(navDeepLink {
    uriPattern = "https://composables.co/blog/{argument}"
}),
) { backStackEntry ->
    val article = backStackEntry.arguments?.getString("argument")
    Text("Showing /$article")
}
}