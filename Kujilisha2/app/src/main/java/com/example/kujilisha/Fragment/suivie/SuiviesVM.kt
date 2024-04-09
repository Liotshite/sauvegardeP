package com.example.kujilisha.Fragment.suivie

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kujilisha.data.DataStateAbn
import com.example.kujilisha.data.DataStatePr
import com.example.kujilisha.model.Abonnement
import com.example.kujilisha.model.Produit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SuiviesVM : ViewModel() {

    val response: MutableState<DataStateAbn> = mutableStateOf(DataStateAbn.Empty)
    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList= mutableListOf<Abonnement>()
        response.value = DataStateAbn.Loading
        FirebaseDatabase.getInstance().getReference("Abonnements")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val abonnementItem = DataSnap.getValue(Abonnement::class.java)
                        if (abonnementItem != null){
                            tempList.add(abonnementItem)
                        }
                        response.value = DataStateAbn.Success(tempList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataStateAbn.Failure(error.message)
                }
            })
    }
}