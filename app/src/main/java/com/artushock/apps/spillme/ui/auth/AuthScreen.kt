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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.base.colors.getButtonColors
import com.artushock.apps.spillme.ui.base.edittext.EditTextField

@Preview
@Composable
fun AuthScreen() {

    var txtLogin by rememberSaveable { mutableStateOf("") }
    var txtPassword by rememberSaveable { mutableStateOf("") }

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
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { },
            colors = getButtonColors()
        ) {
            Text(text = "LOGIN", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
        Text(
            text = "Forget password",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.secondary
        )

    }
}