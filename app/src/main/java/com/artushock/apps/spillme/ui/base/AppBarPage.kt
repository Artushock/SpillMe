package com.artushock.apps.spillme.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.theme.MainBeige
import com.artushock.apps.spillme.ui.theme.MainGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarPage(
    title: String,
    navController: NavHostController,
    navBackEnabled: Boolean = true,
    plusAction: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MainGreen),
                navigationIcon = {
                    if (navBackEnabled) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MainBeige
                            )
                        }
                    }
                },
                actions = {
                    if (plusAction != null) {
                        IconButton(onClick = plusAction) { IconPlus() }
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_spill_me),
                    contentDescription = "Logo SpillMe",
                    modifier = Modifier.align(Alignment.Center)
                )
                content()
            }
        }
    )
}
