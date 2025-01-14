package com.example.threadapp.Navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.threadapp.Screen.Screens
import com.example.threadapp.Screen.addThread
import com.example.threadapp.Screen.bottomNavigation
import com.example.threadapp.Screen.home
import com.example.threadapp.Screen.login
import com.example.threadapp.Screen.notification
import com.example.threadapp.Screen.profle
import com.example.threadapp.Screen.register
import com.example.threadapp.Screen.search
import com.example.threadapp.Screen.splash
import com.example.threadapp.ViewModel.AuthViewModel

@Composable
fun navigation(){
    val navController = rememberNavController();
    val authViewModel:AuthViewModel = viewModel()
    NavHost(navController = navController,
        startDestination = Screens.Splash.route) {
       composable(Screens.Splash.route){
           splash(navController = navController)
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
            addThread(navController)
        }
        composable(Screens.SignIn.route){
            val activity = LocalContext.current as? Activity
           login(navController){
                activity?.finish()
           }
        }
        composable(Screens.SignUp.route){
            register(navController,authViewModel)
        }
        
    }
}