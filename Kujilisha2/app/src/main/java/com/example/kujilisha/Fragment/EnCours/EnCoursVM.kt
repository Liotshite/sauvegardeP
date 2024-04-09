package com.example.kujilisha.Fragment.EnCours

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kujilisha.data.DataStateCom
import com.example.kujilisha.model.Commande
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EnCoursVM: ViewModel() {
    val response: MutableState<DataStateCom> = mutableStateOf(DataStateCom.Empty)
    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList= mutableListOf<Commande>()
        response.value = DataStateCom.Loading
        FirebaseDatabase.getInstance().getReference("Commandes")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val produitItem = DataSnap.getValue(Commande::class.java)
                        if (produitItem != null){
                            tempList.add(produitItem)
                        }
                        response.value = DataStateCom.Success(tempList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataStateCom.Failure(error.message)
                }
            })
    }
}