package com.hexagon.widgetdemoapp.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import kotlinx.coroutines.flow.first


class SimpleWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val selectedBg = getBgFlow(context).first()
        val selectedTheme = getThemeFlow(context).first()

        provideContent {
            SimpleWidgetContent(myBg = selectedBg, myTheme = selectedTheme)
        }
    }
}
