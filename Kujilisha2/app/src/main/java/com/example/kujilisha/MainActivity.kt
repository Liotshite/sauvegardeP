package com.example.kujilisha

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kujilisha.Fragment.login.LoginPage
import com.example.kujilisha.Fragment.presentation.Presentation
import com.example.kujilisha.Fragment.register.RegisterPage
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.ui.theme.Kujilisha2Theme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kujilisha2Theme {
                Loginstart()
            }
        }
    }

}




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Loginstart(){
    val navigationController = rememberNavController()
    NavHost(navController = navigationController,
                startDestination = Screens.Login.screen){
                composable(Screens.Login.screen){ LoginPage(navController = navigationController) }
                composable(Screens.SignUp.screen){ RegisterPage(navController = navigationController) }

    }
}
