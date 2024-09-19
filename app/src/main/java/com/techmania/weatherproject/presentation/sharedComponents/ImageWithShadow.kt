package com.techmania.weatherproject.presentation.sharedComponents


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ImageWithShadow(
    imageResource: Int,
    contentDescription: Int,
    shadowColor: Color = Color.Gray,
    padding: Dp,
    modifier: Modifier,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .offset(x = (-1).dp, y = (1).dp)
                .blur(3.dp),
            colorFilter = ColorFilter.tint(shadowColor)
        )
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(contentDescription),
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}