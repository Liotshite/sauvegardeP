package com.example.kujilisha.model

data class Commande(
    var IdPr:Int?=null,
    var EmailU:String?=null,
    var EmailP:String?=null,
    var Qte:Int?=null,
    var DetailCom:String?=null,
    var validCom:Boolean?=null,
    var DateCom: String? =null) {
}