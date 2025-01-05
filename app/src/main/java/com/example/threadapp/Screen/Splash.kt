package com.example.threadapp.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.threadapp.Navigation.navigation
import kotlinx.coroutines.delay
import com.example.threadapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


@Composable
fun splash(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
         Icon(painter = painterResource(id = R.drawable.thread_logo),
             contentDescription ="thread logo",
             modifier = Modifier.height(100.dp)
         )
        LaunchedEffect(true) {
            delay(1500)
            if (FirebaseAuth.getInstance().currentUser != null){
                navController.navigate(Screens.Home.route)
            }else{
                navController.navigate(Screens.SignIn.route)
            }

        }
    }
}