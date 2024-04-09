package com.example.kujilisha.Fragment

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import com.example.kujilisha.Fragment.CompteP.PreviewSearchBar
import com.example.kujilisha.MainActivity2
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.R
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.model.Produit
import com.example.kujilisha.model.User
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjoutP(){

    Box (modifier = Modifier.fillMaxSize()){
    val database = FirebaseDatabase.getInstance()


    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var Prix by remember { mutableStateOf("") }
    var EmailP by remember { mutableStateOf("") }
    var NomPr by remember { mutableStateOf("") }
    var DescriPr by remember { mutableStateOf("") }
    var IdPr by remember { mutableStateOf("") }
    var CategoriePr by remember { mutableStateOf("") }

    EmailP = sharePreferenceManager.email

    val intent = Intent(context, MainActivity2::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Ajout produit"
            ,color = Green,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
        Row(verticalAlignment = Alignment.CenterVertically){

            Image(bitmap = bitmap.asImageBitmap()
                , contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape)
                    .size(150.dp)
                    .background(Color.White)
                    .border(
                        width = 3.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .background(Color.Gray)
                    .clickable{
                        leunchImage.launch("image/*")
                        showDialog = false
                    }
            )

        }




        OutlinedTextField(
            value = IdPr,
            onValueChange = { IdPr = it },
            label = { Text(text = "Entrer un id numerique") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = NomPr,
            onValueChange = { NomPr = it },
            label = { Text(text = "Nom du produit") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = DescriPr,
            onValueChange = { DescriPr = it },
            label = { Text(text = "Description du produit") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = Prix,
            onValueChange = { Prix = it },
            label = { Text(text = "Prix de l'unité") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = CategoriePr,
            onValueChange = { CategoriePr = it },
            label = { Text(text = "Categorie produit") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                        isUploading.value = true
                        bitmap.let {
                            uploadImageToFirebase(bitmap, context as ComponentActivity){success ->
                                isUploading.value =false
                            }
                        }
                        val produitsref = database.reference.child("Produits")
                        val produitref = produitsref.child(NomPr)
                        val produit = Produit(bitmap.toString(),IdPr.toInt(),NomPr,Prix.toInt(),EmailP,DescriPr,CategoriePr)
                        produitref.setValue(produit)
                        Toast.makeText(context, "Produit ajouté",Toast.LENGTH_SHORT).show()
                        bitmap = img
                        IdPr = ""
                        NomPr = ""
                        Prix = ""
                        EmailP = ""
                        DescriPr = ""
                        CategoriePr = ""

                 },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green, // Couleur d'arrière-plan personnalisée
                contentColor = white // Couleur du contenu (texte) personnalisée
            )

        ) {
            Text(text = "Ajouter")
        }
    }
    }
}

fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity, callback: (Boolean) -> Unit) {
    val storageRef = Firebase.storage.reference
    val imageRef = storageRef.child("images/${bitmap}")

    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
    val imageData = baos.toByteArray()

    imageRef.putBytes(imageData).addOnSuccessListener {
        callback(true)
    }.addOnFailureListener{
        callback(false)
    }
}
