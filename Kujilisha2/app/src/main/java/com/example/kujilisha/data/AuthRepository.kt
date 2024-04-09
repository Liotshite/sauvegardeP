package com.example.kujilisha.data


import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun signIn(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val success = task.isSuccessful
                callback(success)
            }
    }

    fun signUp(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val success = task.isSuccessful
                callback(success)
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}