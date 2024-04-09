package com.example.kujilisha.Fragment.login


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kujilisha.data.AuthRepository
import com.example.kujilisha.data.DataStateU
import com.example.kujilisha.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow

class AuthLoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun signIn(email: String, password: String, callback: (Boolean) -> Unit) {
        authRepository.signIn(email, password) { success ->
            callback(success)
        }

    }


    val response: MutableState<DataStateU> = mutableStateOf(DataStateU.Empty)
    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList= mutableListOf<User>()
        response.value = DataStateU.Loading
        FirebaseDatabase.getInstance().getReference("Users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val userItem = DataSnap.getValue(User::class.java)
                        if (userItem != null){
                            tempList.add(userItem)
                        }
                        response.value = DataStateU.Success(tempList)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataStateU.Failure(error.message)
                }

            })
    }


}
