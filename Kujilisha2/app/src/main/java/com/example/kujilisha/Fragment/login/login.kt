package com.example.kujilisha.Fragment.login



import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kujilisha.MainActivity2
import com.example.kujilisha.Navigation.Screens
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.kujilisha.Fragment.home.AccueilVM
import com.example.kujilisha.Fragment.home.ProduitRow
import com.example.kujilisha.Fragment.home.SetDate
import com.example.kujilisha.Fragment.home.showPr
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.data.DataStatePr
import com.example.kujilisha.data.DataStateU
import com.example.kujilisha.model.Produit
import com.example.kujilisha.model.User
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("StaticFieldLeak", "UnrememberedMutableState", "RememberReturnType",
    "StateFlowValueCalledInComposition"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginPage(authViewModel: AuthLoginViewModel = viewModel(), navController: NavController) {
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val intent = Intent(context, MainActivity2::class.java)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Connexion",
            color = Green,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

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
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        SetDateU(viewModel(),email, password)

            Button(
                onClick = {
                    if(email.isEmpty() || password.isEmpty()){
                        Toast.makeText(context, "Veuillez remplir les champs", Toast.LENGTH_LONG).show()
                        navController.navigate(Screens.Login.screen)
                    }else{
                        authViewModel.signIn(email, password) { success ->
                            if (success) {

                                context.startActivity(intent)

                            } else {
                                navController.navigate(Screens.Login.screen)
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
                Text(text = "Se connecter",)
            }



        TextButton(
            onClick = { navController.navigate(Screens.SignUp.screen) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Créer un compte",
                color = Green)
        }
    }
}





@Composable
fun SetDateU(viewModel: AuthLoginViewModel,email: String, password: String ) {
    when(val result = viewModel.response.value){

        is DataStateU.Success ->{
            assignUser(result.data,email,password)
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
                    Text(text = "une erreur c'est produite lors du chargement des donnees", fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        , color = Color.DarkGray)
                }
            }
        }
        else -> {

        }
    }
}






@Composable
fun assignUser(users: MutableList<User>,email: String, password: String) {

    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    for (user in users) {
        if (email == user.Email) {
            sharePreferenceManager.email = email
            sharePreferenceManager.motpasse = password
            sharePreferenceManager.Nom = user.Nom.toString()
            sharePreferenceManager.Adresse = user.Adresse.toString()
            sharePreferenceManager.Photo = user.Photo.toString()
            sharePreferenceManager.contact = user.Contact.toString()
            if (user.TypeUser == true){
                sharePreferenceManager.TypeUser = "producteur"
            }else {
                sharePreferenceManager.TypeUser = "client"
            }
        }
    }
}