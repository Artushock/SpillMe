package com.artushock.apps.spillme.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artushock.apps.spillme.R
import com.artushock.apps.spillme.ui.theme.MainBeige
import com.artushock.apps.spillme.ui.theme.MainGreen

@Composable
fun SplashScreen(appVersion: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainGreen)
            .padding(16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_spill_me),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
            tint = MainBeige
        )

        Text(
            text = appVersion,
            color = MainBeige,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen("0.0.0")
}