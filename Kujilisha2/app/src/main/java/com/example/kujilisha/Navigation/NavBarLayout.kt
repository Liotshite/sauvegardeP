package com.example.kujilisha.Navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kujilisha.R

@Preview
@Composable
fun NavBarHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logok),
            contentDescription = "Logo_App",
            modifier = Modifier
                .size(140.dp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarBody(
    items : List<NavigationItem>,
    currentRoute: String?,
    onClick : () -> Unit
) {
    items.forEachIndexed { index: Int, navitem: NavigationItem ->
        NavigationDrawerItem(label = {
            Text(text = navitem.title)
        }, selected = currentRoute == navitem.route, onClick = {
            onClick()
        }, icon = {
            Icon(
                imageVector = if (currentRoute == navitem.route) {
                    navitem.selectedIcon
                } else{
                    navitem.unselectedIcon
                }, contentDescription = navitem.title)
        })

    }
}