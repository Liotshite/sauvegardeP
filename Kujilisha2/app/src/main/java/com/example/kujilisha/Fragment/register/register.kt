package com.example.kujilisha.Fragment.register


import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kujilisha.Fragment.uploadImageToFirebase
import com.example.kujilisha.MainActivity2
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.model.User
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white
import com.google.firebase.database.FirebaseDatabase



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(authViewModel: AuthSignUpViewModel = viewModel(), navController: NavController) {

    val database = FirebaseDatabase.getInstance()


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var adresse2 by remember { mutableStateOf("") }
    var nom2 by remember { mutableStateOf("") }
    var TypeU by remember { mutableStateOf(false) }
    var Contact2 by remember { mutableStateOf("") }


    val context = LocalContext.current
    val intent = Intent(context, MainActivity2::class.java)
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).verticalScroll(
                rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Inscription"
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
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = nom2,
            onValueChange = { nom2 = it },
            label = { Text(text = "Nom complet") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = adresse2,
            onValueChange = { adresse2 = it },
            label = { Text(text = "Adresse") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = Contact2,
            onValueChange = { Contact2 = it },
            label = { Text(text = "Numero de téléphone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.clickable { TypeU = !TypeU },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "êtes vous un producteur ?",
                fontWeight = FontWeight.Bold,
                color = if (TypeU) Color.Black else Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
            Checkbox(
                checked = TypeU,
                onCheckedChange = { TypeU= it }
            )
        }

        Button(
            onClick = {
                if(email.isEmpty() && password.isEmpty() && nom2.isEmpty()){
                    Toast.makeText(context, "les champs Nom,email et mot de passe ne peuvent pas etre vide", Toast.LENGTH_LONG).show()
                    navController.navigate(Screens.Login.screen)

                }else{
                    authViewModel.signUp(email, password) { success ->
                        if (success) {
                            val usersref = database.reference.child("Users")
                            val userref = usersref.child(nom2)
                            val user = User(email,password,nom2,adresse2,"",Contact2,TypeU)
                            userref.setValue(user)
                            isUploading.value = true
                            bitmap.let {
                                uploadImageToFirebase(bitmap, context as ComponentActivity){ success ->
                                    isUploading.value =false
                                }
                            }

                            sharePreferenceManager.email = email
                            sharePreferenceManager.motpasse = password
                            sharePreferenceManager.Nom = nom2
                            sharePreferenceManager.Adresse = adresse2
                            sharePreferenceManager.Photo = ""
                            sharePreferenceManager.contact = Contact2

                            if (TypeU == true){
                                sharePreferenceManager.TypeUser = "producteur"
                            }else {
                                sharePreferenceManager.TypeUser = "client"
                            }
                            context.startActivity(intent)
                        } else {
                            navController.navigate(Screens.SignUp.screen)
                        }
                    }
                }
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green, // Couleur d'arrière-plan personnalisée
                contentColor = white // Couleur du contenu (texte) personnalisée
            )

        ) {
            Text(text = "S'inscrire")
        }

        TextButton(
            onClick = { navController.navigate(Screens.Login.screen) },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(text = "Vous avez un compte? vous connecter",color = Green)
        }
    }
}