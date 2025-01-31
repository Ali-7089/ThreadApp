package com.example.threadapp.Screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.threadapp.Model.ThreadData
import com.example.threadapp.R
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.sourceInformation
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.threadapp.Model.User
import com.example.threadapp.ViewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)

fun timestampToReadableTime(timestamp: String):String{
    val instant = Instant.ofEpochMilli(timestamp.toLong())
    var formatter = DateTimeFormatter.ofPattern("hh:mm a, dd-MMM-y")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun home(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val threadAndUser by homeViewModel.threadAndUser.observeAsState(null)
    println(threadAndUser)
    Scaffold(
        bottomBar = { myBottomBar(navController) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.thread_logo), contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(top = 60.dp)
        ) {
            items(threadAndUser ?: emptyList()) { pair ->
                threadItem(pair.first, pair.second)
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun threadItem(threadData: ThreadData, user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Column for main content
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Row for profile picture and username
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Picture
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.imgUrl).build(),
                        contentDescription = "Profile Image",
                        placeholder = painterResource(id = R.drawable.profile_image),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Username
                    Text(
                        text = user.username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Text(
                    text = timestampToReadableTime(threadData.timestamp),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Caption
            Text(
                text = threadData.thread,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Conditionally load the post image if it exists
            if(threadData.imgUrl != ""){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(threadData.imgUrl).build(),
                    contentDescription = "Profile Image",
                    placeholder = painterResource(id = R.drawable.profile_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(vertical = 8.dp),
                    contentScale = ContentScale.Crop
                )

            }


            // Post Time (Top-Right Corner)

        }
    }
}


