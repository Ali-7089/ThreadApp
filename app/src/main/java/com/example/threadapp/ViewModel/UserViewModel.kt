package com.example.threadapp.ViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.Model.ThreadData
import com.example.threadapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {
    var db = FirebaseDatabase.getInstance()
    var threads = db.getReference("threads")
    var users = db.getReference("users")

    private var _threadData = MutableLiveData<List<ThreadData>>()
    var threadData: LiveData<List<ThreadData>> = _threadData

    private var _userData = MutableLiveData<User>()
    var userData: LiveData<User> = _userData

    fun fetchUser(userId:String){
        users.child(userId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(User::class.java)
                data?.let {
                    _userData.postValue(data!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    suspend fun fetchThread(uid:String) = withContext(Dispatchers.IO) {
        println("yaha tk bhi nhi aa paa rhew")
        try {
            val resultList = mutableListOf<ThreadData>()
            threads.orderByChild("uid").equalTo(uid).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    println("snapshot "+ snapshot)
                    for (threadSnapShot in snapshot.children){
                       var thread = threadSnapShot.getValue(ThreadData::class.java)
                        resultList.add(thread!!)
                    }
                    val threadList = resultList // Wait for all user fetches to complete
                    _threadData.postValue(threadList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
            // Update LiveData on UI thread
        } catch (e: Exception) {
            Log.e("Firebase", "Error fetching threads: ${e.message}")
        }
    }

}