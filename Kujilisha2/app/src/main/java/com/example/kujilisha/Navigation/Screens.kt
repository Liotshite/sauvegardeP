package com.example.kujilisha.Navigation

import com.example.kujilisha.Navigation.Screens.Accueil.screen

sealed class Screens (val screen: String){
     object Accueil: Screens("Accueil")
    object Panier : Screens("Panier")
    object En_cours : Screens("En_cours")
    object Suivies : Screens( "Suivi(e)s")
    object Compte : Screens( "Compte")
    object Compte2 : Screens( "Compte2")
    object Presentation : Screens( "presentation")
    object Presentation2 : Screens( "presentation2")
    object AjoutP : Screens("AjoutP")
    object Login : Screens("Login")
    object SignUp : Screens("SignUp")
}