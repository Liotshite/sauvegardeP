package com.example.kujilisha

import android.content.Context

class SharePreferenceManager(
    context: Context
) {
    private val preferences = context.getSharedPreferences(
        context.packageName,
        Context.MODE_PRIVATE
    )
    private val editor = preferences.edit()

    private val keyEmail = "email"
    private val keyMDP = "Motpasse"
    private val keyNom = "Nom"
    private val keyTypeUser = "type"
    private val keyContact = "contact"
    private val keyAdresse = "Adresse"
    private val keyPhoto = "Photo"

    private val keyPhotoPr = "keyPhotoPr"
    private val keyIdPr = "keyIdPr"
    private val keyPrix = "keyPrix"
    private val keyEmailP = "keyEmailP"
    private val keyNomPr = "keyNomPr"
    private val keyDescriPr = "keyDescriPr"
    private val keyCategoriePr = "keyCategoriePr"



    var CategoriePr
        get() = preferences.getString(keyCategoriePr,"").toString()
        set(value) {
            editor.putString(keyCategoriePr,value)
            editor.commit()
        }
    var PhotoPr
        get() = preferences.getString(keyPhotoPr,"").toString()
        set(value) {
            editor.putString(keyPhotoPr,value)
            editor.commit()
        }
    var IdPr
        get() = preferences.getString(keyIdPr,"").toString()
        set(value) {
            editor.putString(keyIdPr,value)
            editor.commit()
        }
    var Prix
        get() = preferences.getInt(keyPrix,0)
        set(value) {
            editor.putInt(keyPrix,value)
            editor.commit()
        }
    var EmailP
        get() = preferences.getString(keyEmailP,"").toString()
        set(value) {
            editor.putString(keyEmailP,value)
            editor.commit()
        }
    var NomPr
        get() = preferences.getString(keyNomPr,"").toString()
        set(value) {
            editor.putString(keyNomPr,value)
            editor.commit()
        }
    var DescriPr
        get() = preferences.getString(keyDescriPr,"").toString()
        set(value) {
            editor.putString(keyDescriPr,value)
            editor.commit()
        }






    var Photo
        get() = preferences.getString(keyPhoto,"").toString()
        set(value) {
            editor.putString(keyPhoto,value)
            editor.commit()
        }
    var Adresse
        get() = preferences.getString(keyAdresse,"").toString()
        set(value) {
            editor.putString(keyAdresse,value)
            editor.commit()
        }
    var TypeUser
        get() = preferences.getString(keyTypeUser, "").toString()
        set(value) {
            editor.putString(keyTypeUser, value)
            editor.apply()
        }
    var Nom
        get() = preferences.getString(keyNom,"").toString()
        set(value) {
            editor.putString(keyNom,value)
            editor.commit()
        }
    var email
        get() = preferences.getString(keyEmail,"").toString()
        set(value) {
            editor.putString(keyEmail,value)
            editor.commit()
        }
    var motpasse
        get() = preferences.getString(keyMDP,"").toString()
        set(value) {
            editor.putString(keyMDP,value)
            editor.commit()
        }
    var contact
        get() = preferences.getString(keyContact,"").toString()
        set(value) {
            editor.putString(keyContact,value)
            editor.commit()
        }


    fun clear(){
        editor.clear()
        editor.commit()
    }

}