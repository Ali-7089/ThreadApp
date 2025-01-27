package com.example.threadapp.Screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.sourceInformation
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.ViewModel.AuthViewModel


@Composable
fun login(navController: NavController,
          authViewModel: AuthViewModel
          ) {

    Column(
        modifier = Modifier
            .fillMaxSize(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        var firebaseUser = authViewModel.firebaseUser.observeAsState()
        var error = authViewModel.error.observeAsState()
        var context = LocalContext.current
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Text("Login",
            style = TextStyle(
                fontSize = 30.sp
            )
            )
        OutlinedTextField(value = email,
            onValueChange = {email = it},
            label = { Text("Enter email...")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(value = password,
            onValueChange = {password = it},
            label = { Text("Enter password...")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true
        )

        Button(onClick = {
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(context,"fill the details",Toast.LENGTH_LONG).show()
            }else{
                authViewModel.login(email, password, context)
                if (firebaseUser.value!=null){
                    navController.navigate(Screens.Home.route)
                }else{
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show()
                }
            }
        }) {
            Text("submit")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text("Don't have an account? Register",
            style = TextStyle(
                fontSize = 15.sp
            ),
            modifier = Modifier.clickable {
                navController.navigate(Screens.SignUp.route){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}