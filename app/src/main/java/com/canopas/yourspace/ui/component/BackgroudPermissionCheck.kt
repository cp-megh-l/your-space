package com.canopas.yourspace.ui.component

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.canopas.yourspace.R
import com.canopas.yourspace.data.utils.openAppSettings
import com.canopas.yourspace.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckBackgroundLocationPermission(
    onDismiss: (() -> Unit)? = null,
    onGranted: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val locationPermissionStates = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        rememberPermissionState(Manifest.permission.ACCESS_BACKGROUND_LOCATION) { granted ->
            if (granted) {
                onGranted?.invoke()
            }
        }
    } else {
        onGranted?.invoke()
        null
    }

    if (locationPermissionStates?.status != PermissionStatus.Granted) {
        ShowBackgroundLocationRequestDialog(
            locationPermissionStates,
            onDismiss = {
                onDismiss?.invoke()
            },
            goToSettings = {
                if (locationPermissionStates?.status?.shouldShowRationale == true) {
                    (context as Activity).openAppSettings()
                } else {
                    locationPermissionStates?.launchPermissionRequest()
                }
            }
        )
    } else {
        onGranted?.invoke()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowBackgroundLocationRequestDialog(
    locationPermissionStates: PermissionState?,
    onDismiss: () -> Unit,
    goToSettings: () -> Unit
) {
    val shouldShowRational = locationPermissionStates?.status?.shouldShowRationale ?: false
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                Modifier
                    .background(AppTheme.colorScheme.containerNormalOnSurface)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.common_background_access_permission_title),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = AppTheme.appTypography.header1
                )
                Text(
                    text = stringResource(id = R.string.common_background_access_permission_message),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    style = AppTheme.appTypography.body2
                )

                Text(
                    text = stringResource(id = if (shouldShowRational) R.string.common_background_access_permission_rational_steps else R.string.common_background_access_permission_steps),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    style = AppTheme.appTypography.body3
                )

                PrimaryButton(
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    label = stringResource(id = R.string.common_background_access_permission_btn),
                    onClick = goToSettings
                )
            }
        }
    }
}