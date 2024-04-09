package com.example.kujilisha.Fragment.EnCours

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.kujilisha.Fragment.CompteP.PreviewSearchBar
import com.example.kujilisha.Fragment.home.AccueilVM
import com.example.kujilisha.Fragment.home.ProduitRow
import com.example.kujilisha.Fragment.home.SetDate
import com.example.kujilisha.Fragment.home.showPr
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.data.DataStateCom
import com.example.kujilisha.data.DataStatePr
import com.example.kujilisha.model.Commande
import com.example.kujilisha.model.Produit
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white


@Composable
fun EnCours(navController: NavController){
    Box (modifier = Modifier.fillMaxSize()){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.width(25.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)

            ) {
                PreviewSearchBar()
            }
            Spacer(modifier = Modifier.width(25.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                SetDateC(viewModel(),navController = navController)
            }
        }
    }
}





@Composable
fun SetDateC(viewModel: EnCoursVM,navController: NavController) {
    when(val result = viewModel.response.value){
        is DataStateCom.Loading ->{
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
        is DataStateCom.Success ->{
            showCom(result.data , navController = navController)
        }
        is DataStateCom.Failure ->{
            Box (modifier = Modifier.fillMaxSize()){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "une erreur c'est produite lors du chargement", fontSize = MaterialTheme.typography.bodyLarge.fontSize
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
fun showCom(commandes: MutableList<Commande>,navController: NavController,viewModel:AccueilVM= viewModel()) {
    val productList = mutableStateListOf<Produit>()
    val commandeList = mutableStateListOf<Commande>()
    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    when (val result = viewModel.response.value) {
        is DataStatePr.Loading -> {
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

        is DataStatePr.Success -> {
            val produits = result.data
            for (com in commandes) {
                if (com.EmailU == sharePreferenceManager.email) {
                    for (produit in produits) {
                        if (com.IdPr == produit.IdPr && com.validCom == true) {
                            productList.add(produit)
                            commandeList.add(com)
                        }
                    }
                }
            }
            showPr2(productList, navController = navController,commandeList)
        }

        is DataStatePr.Failure -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "une erreur c'est produite lors du chargement",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.DarkGray
                    )
                }
            }
        }

        else -> {

        }
    }
}





@Composable
fun showPr2(produits: MutableList<Produit>,navController: NavController,commandes: MutableList<Commande>){
    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }
    LazyColumn {

        items(commandes) { com ->
            ProduitRow2(com,produits){Com->
                for (produit in produits) {
                    if (com.IdPr == produit.IdPr ) {

                        val montant = com.Qte!! * produit.Prix!!
                        sharePreferenceManager.DescriPr = produit.DescriPr.toString()
                        sharePreferenceManager.IdPr = produit.IdPr.toString()
                        sharePreferenceManager.CategoriePr = produit.CategoriePr.toString()
                        sharePreferenceManager.NomPr = produit.NomPr.toString()
                        sharePreferenceManager.PhotoPr = produit.PhotoPr.toString()
                        sharePreferenceManager.EmailP = produit.EmailP.toString()
                        sharePreferenceManager.Prix = produit.Prix!!

                        navController.navigate(Screens.Presentation2.screen)
                    }
                }
            }
        }
    }
}




@Composable
fun ProduitRow2(commande: Commande,
                produits: MutableList<Produit>,
               onItemClick: (Commande) -> Unit = {}){

    var prix by remember { mutableStateOf(0) }

    if (commande.validCom == true){


        for (produit in produits) {
            if (commande.IdPr == produit.IdPr ) {
                prix = produit.Prix!!
            }
        }



        Card (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    onItemClick(commande)
                }
        ){
            Row (verticalAlignment = Alignment.CenterVertically

            ) {


                Column(modifier = Modifier.padding(8.dp)) {


                    Text(
                        text = "Produit: " + commande.DetailCom!!,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Quantité: " + commande.Qte.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "Montant: " + (prix * commande.Qte!!) + " Fc",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1F))
                        Text(
                            text = "Vous sera livré bientot",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(8.dp)

                        )
                    }
                }

            }

        }
    }else{
        Card (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    onItemClick(commande)
                }
        ){
            Row (verticalAlignment = Alignment.CenterVertically
                , modifier = Modifier.background(Color(0xFFA3D9A5))
            ){


                Column(modifier = Modifier.padding(8.dp)) {

                        Text(
                            text = commande.DetailCom!!,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = commande.Qte.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Montant: "+ (prix * commande.Qte!!)+" Fc",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .padding(bottom = 2.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(1F))
                            Text(
                                text = "A été livré",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)

                            )
                        }
                    }
                }
            }
        }
    }

