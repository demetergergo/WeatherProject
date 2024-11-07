package com.techmania.weatherproject.presentation.sharedComponents

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha


@Composable
fun ExpandableCard(expandedState: Boolean = false, onClick: () -> Unit, overView: @Composable (RowScope.() -> Unit), details: @Composable (() -> Unit)) {
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 100,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = {onClick()}) {
        Column(modifier = Modifier.fillMaxWidth()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(10.dp),verticalAlignment = Alignment.CenterVertically) {
                overView()
                Icon(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState)
                        .height(20.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow"
                )
            }
            if (expandedState){
                    details()
            }
        }
    }
}