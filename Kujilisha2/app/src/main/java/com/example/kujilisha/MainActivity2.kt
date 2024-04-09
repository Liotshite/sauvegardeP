package com.example.kujilisha

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kujilisha.Fragment.AjoutP
import com.example.kujilisha.Fragment.home.Accueil
import com.example.kujilisha.Fragment.CompteP.Compte
import com.example.kujilisha.Fragment.CompteP.profil
import com.example.kujilisha.Fragment.EnCours.EnCours
import com.example.kujilisha.Fragment.suivie.Suivies
import com.example.kujilisha.Fragment.home.AccueilVM
import com.example.kujilisha.Fragment.login.LoginPage
import com.example.kujilisha.Fragment.presentation.Presentation
import com.example.kujilisha.Fragment.presentation.Presenvalid
import com.example.kujilisha.Navigation.NavBarHeader
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.Kujilisha2Theme
import kotlinx.coroutines.launch

class MainActivity2 : ComponentActivity() {
    val viewModel: AccueilVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kujilisha2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LearNavBotSheet()
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearNavBotSheet() {
    val navigationController2 = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.Transparent)
                ) {
                    Text(text = "")
                    NavBarHeader()
                }
                Divider()
                NavigationDrawerItem(label = { Text(text = "Accueil") },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Accueil"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController2.navigate(Screens.Accueil.screen) {
                            popUpTo(0)
                        }
                    })
                NavigationDrawerItem(label = { Text(text = "En Cours") },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = "En Cours"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController2.navigate(Screens.En_cours.screen) {
                            popUpTo(0)
                        }
                    })
                NavigationDrawerItem(label = { Text(text = "Suivies") },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddReaction,
                            contentDescription = "Suivies"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController2.navigate(Screens.Suivies.screen) {
                            popUpTo(0)
                        }
                    })

                NavigationDrawerItem(label = { Text(text = "Compte") },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Compte"
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController2.navigate(Screens.Compte.screen) {
                            popUpTo(0)
                        }
                    })
            }
        }

    ) {
        Scaffold(
            topBar = {
                val coroutineScopes = rememberCoroutineScope()
                Surface(shadowElevation = 4.dp) {
                    TopAppBar(title = { Text(text = "Kujilisha", color = Color.White,fontWeight = FontWeight.Bold) },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Green,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White,

                            ),
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScopes.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            }
        ) {padding->
            Column(modifier= Modifier.padding(padding)) {
                NavHost(
                    navController = navigationController2,
                    startDestination = Screens.Accueil.screen
                ) {
                    composable(Screens.Accueil.screen) { Accueil(navController = navigationController2) }
                    composable(Screens.Compte.screen) { Compte(navController = navigationController2) }
                    composable(Screens.En_cours.screen) { EnCours(navController = navigationController2) }
                    composable(Screens.Presentation.screen) { Presentation(navController = navigationController2) }
                    composable(Screens.Presentation2.screen) { Presenvalid() }
                    composable(Screens.Suivies.screen) { Suivies(navController = navigationController2) }
                    composable(Screens.AjoutP.screen) { AjoutP() }
                    composable(Screens.Compte2.screen) { profil(navController = navigationController2) }
                    composable(Screens.Login.screen){ LoginPage(navController = navigationController2) }}
            }
            }

    }
}