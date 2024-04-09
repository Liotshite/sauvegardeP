package com.example.kujilisha.Fragment.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kujilisha.data.DataStatePr
import com.example.kujilisha.model.Produit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccueilVM : ViewModel() {

    val response:MutableState<DataStatePr> = mutableStateOf(DataStatePr.Empty)
    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList= mutableListOf<Produit>()
        response.value = DataStatePr.Loading
        FirebaseDatabase.getInstance().getReference("Produits")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val produitItem = DataSnap.getValue(Produit::class.java)
                        if (produitItem != null){
                            tempList.add(produitItem)
                        }
                        response.value = DataStatePr.Success(tempList)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    response.value = DataStatePr.Failure(error.message)
                }
            })
    }
}