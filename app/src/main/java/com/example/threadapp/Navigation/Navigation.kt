package com.example.threadapp.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threadapp.Screen.Screens
import com.example.threadapp.Screen.addThread
import com.example.threadapp.Screen.home
import com.example.threadapp.Screen.login
import com.example.threadapp.Screen.notification
import com.example.threadapp.Screen.profle
import com.example.threadapp.Screen.register
import com.example.threadapp.Screen.search
import com.example.threadapp.Screen.splash
import com.example.threadapp.ViewModel.AuthViewModel
import com.example.threadapp.ViewModel.HomeViewModel
import com.example.threadapp.ViewModel.ThreadViewModel

@Composable
fun navigation(){
    val navController = rememberNavController();
    val authViewModel:AuthViewModel = viewModel()
    val threadViewModel : ThreadViewModel = viewModel()
    val homeViewModel :HomeViewModel = viewModel()
    NavHost(navController = navController,
        startDestination = Screens.Splash.route) {
       composable(Screens.Splash.route){
           splash(navController = navController)
       }
        composable(Screens.Home.route){
           home(navController = navController,homeViewModel)
        }
        composable(Screens.Profile.route){
            profle(navController,authViewModel)
        }
        composable(Screens.Notification.route){
            notification(navController = navController)
        }
        composable(Screens.Search.route){
            search(navController = navController)
        }
        composable(Screens.AddThread.route){
            addThread(navController,threadViewModel)
        }
        composable(Screens.SignIn.route){
            login(navController = navController, authViewModel = authViewModel)

        }
        composable(Screens.SignUp.route){
            register(navController,authViewModel)
        }
        
    }
}