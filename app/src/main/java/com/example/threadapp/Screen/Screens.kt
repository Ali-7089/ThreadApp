package com.example.threadapp.Screen

sealed class Screens(val route:String){
    object Home:Screens("home")
    object Profile:Screens("profile")
    object Notification:Screens("notification")
    object Search:Screens("search")
    object AddThread:Screens("add-thread")
    object Splash:Screens("splash")
    object BottomNavigation :Screens("bottom-navigation")
}