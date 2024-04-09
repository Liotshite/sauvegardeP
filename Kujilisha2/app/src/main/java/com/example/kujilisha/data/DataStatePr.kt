package com.example.kujilisha.data

import com.example.kujilisha.model.Produit

sealed class DataStatePr{
    class Success (val data: MutableList<Produit>): DataStatePr()
    class Failure (val message: String): DataStatePr()
    object Loading: DataStatePr()
    object Empty: DataStatePr()
}