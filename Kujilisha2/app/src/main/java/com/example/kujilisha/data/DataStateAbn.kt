package com.example.kujilisha.data

import com.example.kujilisha.model.Abonnement

sealed class DataStateAbn{
    class Success (val data: MutableList<Abonnement>): DataStateAbn()
    class Failure (val message: String): DataStateAbn()
    object Loading: DataStateAbn()
    object Empty: DataStateAbn()
}