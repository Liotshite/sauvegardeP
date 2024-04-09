package com.example.kujilisha.Fragment.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.kujilisha.Fragment.CompteP.PreviewSearchBar
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.R
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.data.DataStatePr
import com.example.kujilisha.model.Produit
import com.google.firebase.storage.FirebaseStorage


@Composable
fun Accueil( navController: NavController) {


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
                SetDate(viewModel(),navController = navController)
            }
        }
    }
}



@Composable
fun SetDate(viewModel: AccueilVM,navController: NavController) {
    when(val result = viewModel.response.value){
        is DataStatePr.Loading ->{
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
        is DataStatePr.Success ->{
            showPr(result.data , navController = navController)
        }
        is DataStatePr.Failure ->{
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



@Composable
fun showPr(produits: MutableList<Produit>,navController: NavController){
        val context = LocalContext.current
        val sharePreferenceManager = remember {
            SharePreferenceManager(context)
        }
        LazyColumn {
            items(produits) { produit ->
                ProduitRow(produit){Produit->
                    sharePreferenceManager.DescriPr = produit.DescriPr.toString()
                    sharePreferenceManager.IdPr = produit.IdPr.toString()
                    sharePreferenceManager.CategoriePr = produit.CategoriePr.toString()
                    sharePreferenceManager.NomPr = produit.NomPr.toString()
                    sharePreferenceManager.PhotoPr = produit.PhotoPr.toString()
                    sharePreferenceManager.EmailP = produit.EmailP.toString()
                    sharePreferenceManager.Prix = produit.Prix!!

                    navController.navigate(Screens.Presentation.screen)
                }
            }
        }
    }




@Composable
fun ProduitRow(produit: Produit,
               onItemClick: (Produit) -> Unit = {}){
        Card (modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(produit) }
        ){



            Row (verticalAlignment = Alignment.CenterVertically){

                Image(
                    painter = rememberAsyncImagePainter(model = "gs://my-projet-mobile.appspot.com/images/"+produit.PhotoPr),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = produit.NomPr!!,fontWeight = FontWeight.Bold)
                    Text(text = produit.CategoriePr!!)
                }
            }
        }
}


