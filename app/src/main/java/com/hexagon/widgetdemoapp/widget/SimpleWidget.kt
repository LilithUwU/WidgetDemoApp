package com.hexagon.widgetdemoapp.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import java.util.Calendar
import kotlin.math.min
import kotlinx.coroutines.flow.first



class SimpleWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val prefs = context.dataStore.data.first()
        val selectedBgName = prefs[AppSettings.SELECTED_BACKGROUND] ?: MyBg.Gray.name
        val selectedThemeName = prefs[AppSettings.SELECTED_THEME] ?: MyTheme.Blue.name

        val selectedBg = MyBg.valueOf(selectedBgName)
        val selectedTheme = MyTheme.valueOf(selectedThemeName)

        provideContent {
            SimpleWidgetContent(myBg = selectedBg, myTheme = selectedTheme)
        }
    }
}
