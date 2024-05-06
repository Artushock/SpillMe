package com.artushock.apps.spillme.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import com.artushock.apps.spillme.ui.model.ViewState
import com.artushock.apps.spillme.ui.navigation.NAV_MAIN_LIST

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    var txtLogin by rememberSaveable { mutableStateOf("") }
    var txtPassword by rememberSaveable { mutableStateOf("") }

    val uiState by viewModel.authResultState.collectAsState()

    if (uiState is ViewState.Success && (uiState as ViewState.Success<String>).result.isNotEmpty()) {
        navController.navigate(NAV_MAIN_LIST)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(16.dp, 48.dp, 16.dp, 16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_spill_me), contentDescription = null,
            modifier = Modifier.fillMaxWidth(), tint = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        EditTextField(
            labelText = "Email",
            value = txtLogin,
            onValueChanged = { txtLogin = it })

        EditTextField(
            labelText = "Password",
            value = txtPassword,
            onValueChanged = { txtPassword = it })

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                viewModel.signIn(txtLogin, txtPassword)
            },
            colors = getButtonColors()
        ) {
            when (uiState) {
                is ViewState.Error -> Text(
                    text = "ERROR",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )

                ViewState.Loading -> Text(
                    text = "PROGRESS..",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )

                is ViewState.Success -> {
                    Text(
                        text = "LOGIN",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        Text(
            text = "Forget password",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.secondary
        )

    }
}