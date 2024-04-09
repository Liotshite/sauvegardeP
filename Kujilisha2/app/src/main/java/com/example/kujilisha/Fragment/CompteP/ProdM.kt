package com.example.kujilisha.Fragment.CompteP

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ContentPasteOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.navigation.NavController
import com.example.kujilisha.Fragment.uploadImageToFirebase
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.model.User
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white
import com.google.firebase.database.FirebaseDatabase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profil(navController: NavController){

    val database = FirebaseDatabase.getInstance()

    var TypeU by remember { mutableStateOf(false) }
    var adresse2 by remember { mutableStateOf("") }
    var nom2 by remember { mutableStateOf("") }
    var Contact2 by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    val email = sharePreferenceManager.email
    val nom = sharePreferenceManager.Nom
    val type = sharePreferenceManager.TypeUser
    val Adresse = sharePreferenceManager.Adresse
    val Contact = sharePreferenceManager.contact
    val mdp = sharePreferenceManager.motpasse
    Contact2 = Contact
    adresse2 =  Adresse
    nom2 = nom

    var showDialog by remember{ mutableStateOf(false)}
    val isUploading = remember{ mutableStateOf(false)}
    val img: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(),android.R.drawable.ic_menu_report_image)
    var bitmap by remember { mutableStateOf( img)}
    val leunchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        if( Build.VERSION.SDK_INT < 28 ){
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver,it)
        }else{
            val source = it?.let {it1 ->
                ImageDecoder.createSource(context.contentResolver,it1)
            }
            bitmap = source?.let {it1->
                ImageDecoder.decodeBitmap(it1)
            }!!
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                            .clickable{
                                leunchImage.launch("image/*")
                                showDialog = false
                            }
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = nom,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = type,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                SmallFloatingActionButton(
                    onClick = { navController.navigate(Screens.Compte.screen) },
                    modifier = Modifier.size(50.dp)) {
                    Icon(imageVector = Icons.Default.ContentPasteOff,
                        tint = Color(0xFF1D2328),
                        contentDescription = null)

                }
            }
            Spacer(modifier = Modifier.width(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Modifier les informations ",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    OutlinedTextField(
                        value = nom2,
                        onValueChange = { nom2 = it },
                        label = { Text(text = nom) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = adresse2,
                        onValueChange = { adresse2 = it },
                        label = { Text(text = Adresse) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = Contact2,
                        onValueChange = { Contact2 = it },
                        label = { Text(text = Contact) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier.clickable { TypeU = !TypeU },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Devenir un compte client ?",
                            fontWeight = FontWeight.Bold,
                            color = if (TypeU) Color.Black else Color.Gray,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Checkbox(
                            checked = TypeU,
                            onCheckedChange = { TypeU= it }
                        )
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
                        isUploading.value = true
                        bitmap.let {
                            uploadImageToFirebase(bitmap, context as ComponentActivity){ success ->
                                isUploading.value =false
                            }
                        }
                        val userref = database.reference.child("Users/"+nom )
                        val updatedData = User(email,mdp,nom2,adresse2,bitmap.toString(),Contact2,TypeU)
                        val x = updatedData.toMap()
                        userref.updateChildren(x)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Informations modifiées ", Toast.LENGTH_SHORT).show()
                            }
                    },
                    modifier = Modifier
                        .padding(10.dp)                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green, // Couleur d'arrière-plan personnalisée
                        contentColor = white // Couleur du contenu (texte) personnalisée
                    )
                ) {
                    Text(text = "Valider")
                }
            }
            }
        }
    }