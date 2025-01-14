package com.example.threadapp.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadapp.Model.SharedPref
import com.example.threadapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var db = FirebaseDatabase.getInstance()

    var userRef = db.getReference("users")

    private var _firebaseUser = MutableLiveData<FirebaseUser?>()
    var firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

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
        username: String,
        context: Context
    ) {
       auth.createUserWithEmailAndPassword(email,password)
           .addOnCompleteListener{
               if (it.isSuccessful) {
                   _firebaseUser.value = auth.currentUser
                   saveDate(email,password,name,username,auth.currentUser?.uid, context)
               } else {
                   _error.value = "Something went wrong!!"
               }
           }
    }

    private fun saveDate(email: String, password: String, name: String, username: String, uid: String?,context:Context) {
        val userObject = User(name, username, email, password,uid)

        userRef.child(uid!!).setValue(userObject)
            .addOnSuccessListener {
              SharedPref.storeData(name,username,email,uid, context)
            }
            .addOnFailureListener{

            }

    }

    fun logOut(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}