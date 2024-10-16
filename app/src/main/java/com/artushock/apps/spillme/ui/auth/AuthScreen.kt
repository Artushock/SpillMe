package com.artushock.apps.spillme.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.auth.models.AuthScreenStateModel
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState: AuthScreenStateModel by viewModel.authResultState.collectAsState()

    val exit = viewModel.signInChannel.receiveAsFlow()
    LaunchedEffect(1) {
        viewModel.init()
        exit.collectLatest {
            navController.navigate("mainListScreen")
        }
    }

    AuthScreenSuccess(
        model = uiState,
        onEmailChanged = viewModel::emailChanged,
        onPasswordChanged = viewModel::passwordChanged,
        onSignInClicked = viewModel::signIn,
    )
}

@Composable
private fun AuthScreenSuccess(
    model: AuthScreenStateModel,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignInClicked: () -> Unit,
) {

    var txtLogin by remember { mutableStateOf(model.email) }
    var txtPassword by remember { mutableStateOf(model.password) }

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
            isError = false,
            onValueChanged = {
                txtLogin = it
                onEmailChanged(it)
            })

        EditTextField(
            labelText = "Password",
            value = txtPassword,
            isError = false,
            onValueChanged = {
                txtPassword = it
                onPasswordChanged(it)
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            onClick = onSignInClicked,
            enabled = model.buttonEnabled,
            colors = getButtonColors()
        ) {
            if (model.isProgress) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.background
                )
            }
            Text(
                text = if (!model.isProgress) "LOGIN" else "",
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        Text(
            text = "Forget password",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.secondary
        )

    }
}