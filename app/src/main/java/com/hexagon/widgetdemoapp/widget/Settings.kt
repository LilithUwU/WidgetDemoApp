package com.hexagon.widgetdemoapp.widget

import androidx.compose.ui.graphics.Color

enum class MyBg(val color: Color) {
    Gray(Color(0xFF424242)),
    Blue(Color(0xFF1565C0)),
    Green(Color(0xFF009688)),
    White(Color(0xFFFFFFFF))
}



enum class MyTheme(
    val past: Color,
    val present: Color,
    val future: Color,
) {
    Blue(past = Color(0xFF1565C0), present = Color(0xFF64B5F6), future = Color(0xFFE3F2FD) ),
    Gray(past = Color(0xFF424242), present = Color(0xFF9E9E9E), future = Color(0xFFF5F5F5)),
    Purple(past = Color(0xFF512DA8), present = Color(0xFFBA68C8), future = Color(0xFFF3E5F5)),
    Green(past = Color(0xFF2E7D32), present = Color(0xFF81C784), future = Color(0xFFE8F5E9));

    fun select(dayCounter: Int, today: Int): Color = when {
        dayCounter < today -> past
        dayCounter == today -> present
        else -> future
    }
}
