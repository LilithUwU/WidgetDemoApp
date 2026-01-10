package com.hexagon.widgetdemoapp.widget

import android.content.Context
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
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import java.util.Calendar
import kotlin.math.min
import androidx.glance.background

private fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}

//    todo add widget theme, customization, colors, transparency


class SimpleWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            SimpleWidgetContent()
        }
    }

    @Composable
    private fun SimpleWidgetContent() {
        val cal = Calendar.getInstance()
        val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)
        val currentYear = cal.get(Calendar.YEAR)
        val isLeap = isLeapYear(currentYear)
        val myTheme = MyTheme.Blue
        val myBg = MyBg.Gray.color
        val bitmap = remember(dayOfYear, isLeap) {
            val daysInMonths =
                intArrayOf(31, if (isLeap) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

            val width = 620 // Add space for labels
            val height = 280 // Add space for labels
            val labelPadding = 40f

            val bitmap = createBitmap(width, height)
            val canvas = android.graphics.Canvas(bitmap)
            val paint = android.graphics.Paint()

            val gridWidth = width - labelPadding
            val gridHeight = height - labelPadding
            val cellWidth = gridWidth / 31f
            val cellHeight = gridHeight / 12f
            var dayCounter = 0

            paint.textAlign = android.graphics.Paint.Align.CENTER
            paint.textSize = 12f
            paint.color = android.graphics.Color.WHITE

            // Draw month labels (1-12)
            for (month in 0..11) {
                val textY =
                    labelPadding + month * cellHeight + cellHeight / 2 - (paint.descent() + paint.ascent()) / 2
                canvas.drawText((month + 1).toString(), labelPadding / 2, textY, paint)
            }

            // Draw day labels (1-31)
            for (day in 1..31) {
                val textX = labelPadding + (day - 1) * cellWidth + cellWidth / 2
                canvas.drawText(day.toString(), textX, labelPadding / 2, paint)
            }

            for (month in 0..11) { // 12 months (rows)
                val daysInCurrentMonth = daysInMonths[month]
                for (day in 1..daysInCurrentMonth) { // days in month (columns)
                    dayCounter++

                    paint.color = myTheme.select(dayCounter, dayOfYear).toArgb()

                    val cx = labelPadding + (day - 1) * cellWidth + cellWidth / 2f
                    val cy = labelPadding + month * cellHeight + cellHeight / 2f
                    val radius = (min(cellWidth, cellHeight) / 2f) - 2f
                    canvas.drawCircle(cx, cy, radius, paint)
                }
            }
            bitmap
        }

        Column(
            modifier = GlanceModifier.fillMaxSize().padding(end = 8.dp)
                .background(myBg),
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        ) {
            Image(
                provider = ImageProvider(bitmap),
                contentDescription = "Year grid with month and day labels",
                modifier = GlanceModifier.fillMaxWidth().defaultWeight()
            )
        }
    }
}
