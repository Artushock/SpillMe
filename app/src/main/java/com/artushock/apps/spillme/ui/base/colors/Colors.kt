package com.artushock.apps.spillme.ui.base.colors


import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable

@Composable
fun getTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.secondary,
    focusedBorderColor = MaterialTheme.colorScheme.secondary,
    focusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
    focusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
    focusedLabelColor = MaterialTheme.colorScheme.secondary,
    focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
    focusedSupportingTextColor = MaterialTheme.colorScheme.secondary,
    focusedPrefixColor = MaterialTheme.colorScheme.secondary,
    focusedSuffixColor = MaterialTheme.colorScheme.secondary,
)


@Composable
fun getButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = MaterialTheme.colorScheme.secondary,
    contentColor = MaterialTheme.colorScheme.background,
    disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
    disabledContentColor = MaterialTheme.colorScheme.background,
)