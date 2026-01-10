package com.hexagon.widgetdemoapp.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyBox(iconTheme: Color, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(iconTheme),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun Day(iconColor: Color) {
    Icon(
        imageVector = Icons.Default.Circle,
        contentDescription = null,
        tint = iconColor,
        modifier = Modifier.size(10.dp)
    )
}