package com.example.threadapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadapp.Model.ThreadData
import com.example.threadapp.Model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel:ViewModel() {
    var db = FirebaseDatabase.getInstance()
    var threadRef = db.getReference("threads")
    var users = db.getReference("users")

    private var _threadAndUser = MutableLiveData<List<Pair<ThreadData,User>>>()
    var threadAndUser: LiveData<List<Pair<ThreadData,User>>> = _threadAndUser
    init {
       fetchThreadAndUser(){
        _threadAndUser.value = it
       }
    }

    fun fetchThreadAndUser(onResult:(List<Pair<ThreadData,User>>)->Unit){
        println("mai to aaya")
        threadRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var result = mutableListOf<Pair<ThreadData,User>>()
              for(threadSnapShot in snapshot.children){
                var thread = threadSnapShot.getValue(ThreadData::class.java)
                  thread?.let {
                      fetchUser(thread){
                          user->
                        result.add(0,thread to user )

                        if(result.size == snapshot.childrenCount.toInt()) {
                            onResult(result)
                        }
                      }
                  }
              }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUser(thread:ThreadData,onResult:(User)->Unit){
         users.child(thread.uid!!).addListenerForSingleValueEvent(object :ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)
                 user?.let {
                     onResult(user)
                 }
             }

             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }

         })
    }
}


