package com.example.kujilisha.Fragment.presentation

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.rememberImagePainter
import com.example.kujilisha.Fragment.login.AuthLoginViewModel
import com.example.kujilisha.SharePreferenceManager
import com.example.kujilisha.data.DataStateU
import com.example.kujilisha.ui.theme.Green
import com.example.kujilisha.ui.theme.white


@Composable
fun Presenvalid() {

    val context = LocalContext.current
    val sharePreferenceManager = remember {
        SharePreferenceManager(context)
    }

    val nompr = sharePreferenceManager.NomPr
    val DescriPr = sharePreferenceManager.DescriPr
    val PhotoPr = sharePreferenceManager.PhotoPr
    val Prix = sharePreferenceManager.Prix
    val emailp = sharePreferenceManager.EmailP



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
                            .size(350.dp, 180.dp)
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
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Text(
                        text = nompr,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
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

                            Text(text = Prix.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = white,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))

                        }

                    }
                }
            }
        }
    }
}



