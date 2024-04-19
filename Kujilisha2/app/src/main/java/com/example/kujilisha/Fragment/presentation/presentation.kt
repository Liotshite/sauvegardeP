package com.example.kujilisha.Fragment.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DoDisturbOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.kujilisha.Fragment.login.AuthLoginViewModel
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.data.DataStateU
import com.example.kujilisha.model.Commande
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.time.LocalDate

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Presentation(navController: NavController) {

    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    val database = FirebaseDatabase.getInstance()
    val counter = remember{ mutableStateOf(0) }

    val email = sharePreferenceManager.email
    val nompr = sharePreferenceManager.NomPr
    val DescriPr = sharePreferenceManager.DescriPr
    val PhotoPr = sharePreferenceManager.PhotoPr
    val Prix = sharePreferenceManager.Prix
    val  idPr = sharePreferenceManager.IdPr
    val emailp = sharePreferenceManager.EmailP

    val currentDateMillis = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val currentDateString = dateFormat.format(java.util.Date(currentDateMillis))
    Box(modifier = Modifier.fillMaxSize()

    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1D2328))
                    .height(200.dp),
                contentAlignment = Alignment.Center

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberImagePainter(PhotoPr),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .size(365.dp, 180.dp)
                            .background(Color(0xFF119417)),
                        contentScale = ContentScale.FillWidth
                    )

                }
            }



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 3.dp)
                ) {

                    Text(
                        text = nompr,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                }

                Spacer(modifier = Modifier.width(20.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .fillMaxWidth()
                        .background(Color.LightGray)

                ) {
                    Column {

                        Text(
                            text = DescriPr,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Produit par "+prod(viewModel(), emailp ),
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                        )



                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .padding(bottom = 2.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .background(Color(0xFF1D2328))
                                .height(42.dp)

                        ){


                            SmallFloatingActionButton(
                                onClick = { counter.value++},
                                modifier = Modifier.size(25.dp)) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    tint = Color(0xFF1D2328),
                                    contentDescription = null )
                            }

                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "${counter.value.toString()}",
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(10.dp))
                            SmallFloatingActionButton(
                                onClick = { if(counter.value > 0){
                                    counter.value--}else{}},
                                modifier = Modifier.size(25.dp)) {
                                Icon(imageVector = Icons.Default.DoDisturbOn,
                                    tint = Color(0xFF1D2328),
                                    contentDescription = null)

                            }

                            Spacer(modifier = Modifier.weight(1F))

                            Text(text = (Prix * counter.value).toString()+"Fc",
                                style = MaterialTheme.typography.bodyMedium,
                                color = white,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )

                        }

                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 36.dp)
                    ,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            val commandesref = database.reference.child("Commandes")
                            val commanderef = commandesref.child(nompr)
                            val commande = Commande(idPr.toInt(),email,emailp,counter.value,nompr,true,currentDateString)
                            commanderef.setValue(commande)
                            Toast.makeText(context, "Votre commande a bien été reçu", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.Accueil.screen)
                        },
                        modifier = Modifier
                            .padding(10.dp)                    ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green, // Couleur d'arrière-plan personnalisée
                            contentColor = white // Couleur du contenu (texte) personnalisée
                        )
                    ) {
                        Text(text = "Commander")
                    }
                }
            }
        }
    }
}





@Composable
fun prod(viewModel: AuthLoginViewModel, email: String): String {
    when (val result = viewModel.response.value) {
        is DataStateU.Success -> {
            val users = result.data
            for (user in users) {
                if (email == user.Email) {
                    return user.Nom.toString()
                }
            }
            return "echec 1"
        }

        is DataStateU.Failure -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "une erreur c'est produite lors du chargement presentation",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.DarkGray
                    )
                    return "echec 2"
                }
            }
        }

        else -> {
                return "Chargement"
        }
    }
    return "Provide the return value"
}


