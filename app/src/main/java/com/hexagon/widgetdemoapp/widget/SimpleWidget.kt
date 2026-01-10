package com.hexagon.widgetdemoapp.widget

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import kotlinx.coroutines.flow.first


class SimpleWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val bgFlow = getBgFlow(context)
        val themeFlow = getThemeFlow(context)

        provideContent {
            val selectedBg by bgFlow.collectAsState(initial = MyBg.Gray)
            val selectedTheme by themeFlow.collectAsState(initial = MyTheme.Blue)

            SimpleWidgetContent(myBg = selectedBg, myTheme = selectedTheme)
        }
    }
}
