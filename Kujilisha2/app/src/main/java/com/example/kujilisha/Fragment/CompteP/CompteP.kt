package com.example.kujilisha.Fragment.CompteP

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material.icons.filled.DoDisturbOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kujilisha.Fragment.home.AccueilVM
import com.example.kujilisha.Fragment.home.ProduitRow
import com.example.kujilisha.Fragment.login.AuthLoginViewModel
import com.example.kujilisha.Fragment.uploadImageToFirebase
import com.example.kujilisha.MainActivity2
import com.example.kujilisha.Navigation.Screens
import com.example.kujilisha.R
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.data.DataStatePr
import com.example.kujilisha.model.Produit
import com.example.kujilisha.model.User
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white
import com.google.firebase.database.FirebaseDatabase


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Compte(navController: NavController) {


    val database = FirebaseDatabase.getInstance()

    var TypeU by remember { mutableStateOf(false) }
    var adresse2 by remember { mutableStateOf("")}
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


            if (type == "producteur") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                            .clickable {
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
                        onClick = { navController.navigate(Screens.Compte2.screen) },
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentPasteGo,
                            tint = Color(0xFF1D2328),
                            contentDescription = null
                        )

                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
                {
                    PreviewSearchBar()
                    Spacer(modifier = Modifier.width(8.dp))
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {

                    Text(
                        text = "Vos produits",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { navController.navigate(Screens.AjoutP.screen) },
                        modifier = Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green, // Couleur d'arrière-plan personnalisée
                            contentColor = white // Couleur du contenu (texte) personnalisée
                        )
                    ) {
                        Text(text = "Ajouter")
                    }

                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SetDateU(
                        viewModel(),
                        navController = navController,
                        email
                    )
                }
            }
            } else {

                Contact2 = Contact
                adresse2 =  Adresse
                nom2 = nom
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                                .clickable {
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
                    }

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
                            label = { Text(text = "Nom complet") },
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
                                text = "Devenir un compte producteur ?",
                                fontWeight = FontWeight.Bold,
                                color = if (TypeU) Color.Black else Color.Gray,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Checkbox(
                                checked = TypeU,
                                onCheckedChange = { TypeU = it }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 36.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                isUploading.value = true
                                bitmap.let {
                                    uploadImageToFirebase(
                                        bitmap,
                                        context as ComponentActivity
                                    ) { success ->
                                        isUploading.value = false
                                    }
                                }
                                val userref = database.reference.child("Users/" + nom)
                                val updatedData = User(
                                    email,
                                    mdp,
                                    nom2,
                                    adresse2,
                                    bitmap.toString(),
                                    Contact2,
                                    TypeU
                                )
                                val x = updatedData.toMap()
                                userref.updateChildren(x)
                                    .addOnSuccessListener {
                                        // Mise à jour réussie
                                    }


                            },
                            modifier = Modifier
                                .padding(10.dp),
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
    }




fun User.toMap(): Map<String, Any?> {
    return mapOf(
        "Email" to Email,
        "Nom" to Nom,
        "Adresse" to Adresse,
        "Photo" to Photo,
        "Contact" to Contact,
        "TypeUser" to TypeUser
    )
}




@Composable
fun SetDateU(viewModel: AccueilVM, navController: NavController,email: String) {
    when(val result = viewModel.response.value){
        is DataStatePr.Loading ->{
            Box(modifier = Modifier.fillMaxSize()
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
            showPrU(result.data , navController = navController,email)
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
                    Text(text = "une erreur c'est produite lors du chargement compte", fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        , color = Color.DarkGray)
                }
            }
        }
        else -> {

        }
    }
}



@Composable
fun showPrU(produits: MutableList<Produit>, navController: NavController,email: String){

    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    LazyColumn {
        items(produits) { produit ->
            if (email == produit.EmailP) {
                ProduitRow(produit) { Produit ->

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
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onClear: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                onSearch(newText)
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 7.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text("Rechercher") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        )

        if (text.isNotEmpty()) {
            IconButton(
                onClick = {
                    text = ""
                    onClear()
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear"
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(onSearch = {}, onClear = {})
}



