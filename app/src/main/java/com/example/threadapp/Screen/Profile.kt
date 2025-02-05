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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.Model.ThreadData
import com.example.threadapp.R
import com.example.threadapp.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
     val allThreads by userViewModel.threadData.observeAsState(emptyList())

      LaunchedEffect(true) {
          FirebaseAuth.getInstance()?.currentUser?.uid?.let {
              userViewModel.fetchThread(FirebaseAuth.getInstance()?.currentUser?.uid.toString())
          }
      }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        ProfileHeader(authViewModel,navController)

        Scaffold(
            bottomBar = { myBottomBar(navController) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(allThreads?: emptyList()) {
                    thread->
                    PostItem(thread)
                }
            }
        }

    }
}

@Composable
fun ProfileHeader(authViewModel: AuthViewModel,
                  navController:NavController) {
    var firebaseUser = authViewModel.firebaseUser.observeAsState()
     var context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(SharedPref.getImage(context)).build(),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = SharedPref.getName(context), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Android developer", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "500",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Followers", fontSize = 14.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "250",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Following", fontSize = 14.sp, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        Button(
            onClick = {
                authViewModel.logOut()
                if (firebaseUser.value == null){
                    println(firebaseUser.value)
                    navController.navigate(Screens.SignIn.route)
                }},
        ) {
            Text(text = "Logout", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun PostItem(threadData: ThreadData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//        elevation = 4.dp
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(threadData.imgUrl).build(),
            contentDescription = "user post",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}



/*@Composable
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
 */