package com.example.threadapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var db = FirebaseDatabase.getInstance()

    var database = db.getReference("users")

    private var _firebaseUser = MutableLiveData<FirebaseUser>()
    var firebaseUser: LiveData<FirebaseUser> = _firebaseUser

    private var _error = MutableLiveData<String>()
    var error: LiveData<String> = _error

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.value = auth.currentUser
                } else {
                    _error.value = "Something went wrong!!"
                }
            }
    }

    fun register(
        email: String,
        password: String,
        name: String,
        username: String
    ) {
       auth.createUserWithEmailAndPassword(email,password)
           .addOnCompleteListener{
               if (it.isSuccessful) {
                   _firebaseUser.value = auth.currentUser
               } else {
                   _error.value = "Something went wrong!!"
               }
           }
    }


}