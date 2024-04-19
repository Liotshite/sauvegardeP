package com.example.kujilisha.Fragment.suivie

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kujilisha.Fragment.CompteP.PreviewSearchBar
import com.example.kujilisha.Fragment.home.AccueilVM
import com.example.kujilisha.Fragment.login.AuthLoginViewModel
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.R
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.data.DataStateAbn
import com.example.kujilisha.data.DataStateU
import com.example.kujilisha.model.Abonnement
import com.example.kujilisha.model.Commande
import com.example.kujilisha.model.Produit
import com.example.kujilisha.model.User
import com.google.firebase.database.FirebaseDatabase
import java.net.URLEncoder


@Composable
fun Suivies(navController: NavController){
    Box (modifier = Modifier.fillMaxSize()){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            // Debut
            Spacer(modifier = Modifier.width(20.dp))
            Row(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
                {
                    PreviewSearchBar()
                    Spacer(modifier = Modifier.width(8.dp))
                }


            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(bottom = 2.dp)
            ) {

                Text(
                    text = "Des propositions pour vous",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            SetDateU(viewModel(),navController = navController)
        }
    }
}





@SuppressLint("UnrememberedMutableState")
@Composable
fun SetDateU(viewModel2: SuiviesVM,navController: NavController) {

    when (val result2 = viewModel2.response.value) {
        is DataStateAbn.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "")
                val color by infiniteTransition.animateColor(
                    initialValue = MaterialTheme.colorScheme.inverseOnSurface,
                    targetValue = MaterialTheme.colorScheme.surfaceVariant,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                LazyColumn {
                    items(5) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(color)
                                .height(100.dp)
                                .fillMaxWidth()
                        )
                    }
                }

            }

        }

        is DataStateAbn.Success -> {
            showCom(result2.data, navController = navController)
        }

        is DataStateAbn.Failure -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "une erreur s'est produite lors du chargement des abonnements",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.DarkGray
                    )
                }
            }
        }

        else -> {}
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun showCom(abonnements: MutableList<Abonnement>, navController: NavController, viewModel: AuthLoginViewModel = viewModel()) {


    when(val result = viewModel.response.value){
        is DataStateU.Loading ->{
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)){
                val infiniteTransition = rememberInfiniteTransition(label = "")
                val color by infiniteTransition.animateColor(
                    initialValue = MaterialTheme.colorScheme.inverseOnSurface,
                    targetValue = MaterialTheme.colorScheme.surfaceVariant,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = FastOutLinearInEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = ""
                )
                LazyColumn{
                    items(5){
                        Box(modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(color)
                            .height(100.dp)
                            .fillMaxWidth()
                        )
                    }
                }

            }


        }

        is DataStateU.Success ->{

            userPr(result.data,abonnements,navController = navController)
        }

        is DataStateU.Failure ->{
            Box (modifier = Modifier.fillMaxSize()){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "une erreur s'est produite lors du chargement des utilisateurs", fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        , color = Color.DarkGray)
                }
            }
        }
        else -> {

        }
    }
}






@SuppressLint("UnrememberedMutableState")
@Composable
fun userPr(users: MutableList<User>, abonnements: MutableList<Abonnement>,navController: NavController){
    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }
    val email = sharePreferenceManager.email
    var nom2 = sharePreferenceManager.Nom

    var userSuivi = mutableStateListOf<User>()
    var userPasSuivie = mutableStateListOf<User>()

    for (abn in abonnements){
        for (user in users){
                if (email == abn.EmailClt && abn.EmailPrt == user.Email ) {
                    userSuivi.add(user)
                } else {
                    if (user.TypeUser == true){
                        nom2 += user.Nom
                        userPasSuivie.add(user)
                    }
                }
        }
    }
    LazyColumn {
        items(userPasSuivie) { user ->
            UsersRow(user,email,nom2){User->

            }
        }
    }
}






@Composable
fun UsersRow(user: User,email:String,nom:String,
               onItemClick: (User) -> Unit = {}){
    Card (modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable {
            onItemClick(user)
        }
    ){

        val context = LocalContext.current

        val database = FirebaseDatabase.getInstance()
        val x = "gs://my-projet-mobile.appspot.com/images/"+user.Photo
        val encodedUrl = URLEncoder.encode(x,"UTF-8")
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(encodedUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.galeri),
                contentDescription = "Profil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).size(30.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = user.Nom!!,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = user.Adresse!!,
                    style = MaterialTheme.typography.bodySmall
                )
                Row( verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(8.dp)) {
                    Spacer(modifier = Modifier.weight(1f))
                    SmallFloatingActionButton(
                        onClick = {
                            val abonnementsref = database.reference.child("Abonnements")
                            val abonnementref = abonnementsref.child(nom)
                            val abonnement = Abonnement(user.Email,email)
                            abonnementref.setValue(abonnement)

                            Toast.makeText(context, "Abonnement validé", Toast.LENGTH_SHORT).show()

                        },
                        modifier = Modifier.size(50.dp)) {
                        Icon(imageVector = Icons.Default.AddBox,
                            tint = Color(0xFF1D2328),
                            contentDescription = null)
                    }
                }
            }
        }
    }
}
