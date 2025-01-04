package com.example.threadapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threadapp.Screen.Screens
import com.example.threadapp.Screen.bottomNavigation
import com.example.threadapp.Screen.home
import com.example.threadapp.Screen.notification
import com.example.threadapp.Screen.profle
import com.example.threadapp.Screen.search
import com.example.threadapp.Screen.splash

@Composable
fun navigation(){
    val navController = rememberNavController();
    NavHost(navController = navController,
        startDestination = Screens.Splash.route) {
       composable(Screens.Splash.route){
           splash(navController = navController)
       }
        composable(Screens.BottomNavigation.route){
             bottomNavigation(navController)
        }
        composable(Screens.Home.route){
           home(navController = navController)
        }
        composable(Screens.Profile.route){
            profle(navController = navController)
        }
        composable(Screens.Notification.route){
            notification(navController = navController)
        }
        composable(Screens.Search.route){
            search(navController = navController)
        }
        composable(Screens.AddThread.route){

        }
        
    }
}