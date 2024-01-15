package com.canopas.catchme.ui.flow.onboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.canopas.catchme.R
import com.canopas.catchme.ui.component.PrimaryButton
import com.canopas.catchme.ui.theme.AppTheme

@Composable
fun SpaceInfoOnboard(firstName: String, onContinue: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.surface)
            .padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        TitleContent(firstName)
        Spacer(modifier = Modifier.weight(0.5f))

        Image(
            painter = painterResource(id = R.drawable.ic_onboard_space_intro),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.weight(0.5f))
        SubTitleTitleContent()
        Spacer(modifier = Modifier.height(30.dp))
        PrimaryButton(label = stringResource(R.string.common_btn_continue), onClick = onContinue)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun TitleContent(firstName: String) {
    Text(
        text = stringResource(R.string.onboard_space_info_title, firstName),
        style = AppTheme.appTypography.header1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
    )
}

@Composable
private fun SubTitleTitleContent() {
    Text(
        text = stringResource(R.string.onboard_space_info_subtitle),
        style = AppTheme.appTypography.body1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
    )
}
