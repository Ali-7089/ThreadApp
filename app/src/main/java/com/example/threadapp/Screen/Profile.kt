package com.example.threadapp.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.threadapp.ViewModel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun profle(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var firebaseUser = authViewModel.firebaseUser.observeAsState()
    Scaffold(
        bottomBar = { myBottomBar(navController) }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {

        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
         Button(onClick = {
             authViewModel.logOut()
             if (firebaseUser.value == null){
                 println(firebaseUser.value)
                 navController.navigate(Screens.SignIn.route)
             }
         }) {
             Text(text = "log out")
         }
    }
}