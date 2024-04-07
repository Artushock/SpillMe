package com.artushock.apps.spillme.ui.base

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.artushock.apps.spillme.R

@Composable
fun IconPlus(size: Int? = null) = Icon(
    painter = painterResource(id = R.drawable.ic_plus_24),
    contentDescription = "Plus image",
    modifier = size?.let { Modifier.size(it.dp) } ?: Modifier,
    tint = MaterialTheme.colorScheme.tertiary
)