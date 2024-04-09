package com.example.kujilisha.data

import com.example.kujilisha.model.Commande

sealed class DataStateCom{
    class Success (val data: MutableList<Commande>): DataStateCom()
    class Failure (val message: String): DataStateCom()
    object Loading: DataStateCom()
    object Empty: DataStateCom()
}