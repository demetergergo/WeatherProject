package com.techmania.weatherproject.presentation.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.techmania.weatherproject.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestSpecificPermission(permission: String) {
    var showDialogState by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val permissionState = rememberPermissionState(
        permission
    )
    if (!permissionState.status.isGranted) {
        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }
        LaunchedEffect(permissionState.status) {
            if (permissionState.status.shouldShowRationale
            ) {
                showDialogState = true
            }
        }
        if (showDialogState) {
            AlertDialog(
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = { showDialogState = false },
                title = { Text(stringResource(R.string.permission_required)) },
                text = { Text(stringResource(R.string.permission_denied)) },
                confirmButton = {
                    Button(onClick = {
                        navigateToAppDetails(context)
                    }) {
                        Text(stringResource(R.string.open_settings))
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialogState = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )

        }
    }
}

fun navigateToAppDetails(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    context.startActivity(intent)
    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS))
}