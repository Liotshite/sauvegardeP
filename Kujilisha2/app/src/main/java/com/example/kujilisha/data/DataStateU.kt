package com.example.kujilisha.data

import com.example.kujilisha.model.User

sealed class DataStateU{
    class Success (val data: MutableList<User>): DataStateU()
    class Failure (val message: String): DataStateU()
    object Loading: DataStateU()
    object Empty: DataStateU()
}