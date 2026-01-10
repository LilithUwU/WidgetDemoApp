package com.hexagon.widgetdemoapp.widget

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import java.util.Calendar
import kotlin.math.min
private fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}
@Composable
fun SimpleWidgetContent(myBg: MyBg, myTheme: MyTheme) {
    val cal = Calendar.getInstance()
    val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)
    val currentYear = cal.get(Calendar.YEAR)
    val isLeap = isLeapYear(currentYear)

    val bitmap = remember(dayOfYear, isLeap, myTheme) {
        createYearGridBitmap(dayOfYear, isLeap, myTheme)
    }

    Column(
        modifier = GlanceModifier.fillMaxSize().padding(end = 8.dp)
            .background(myBg.color),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
    ) {
        Image(
            provider = ImageProvider(bitmap),
            contentDescription = "Year grid with month and day labels",
            modifier = GlanceModifier.fillMaxWidth().defaultWeight()
        )
    }
}

private fun createYearGridBitmap(dayOfYear: Int, isLeap: Boolean, myTheme: MyTheme): Bitmap {
    val daysInMonths =
        intArrayOf(31, if (isLeap) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    // Dimensions
    val width = 620
    val height = 280
    val labelPadding = 40f
    val gridWidth = width - labelPadding
    val gridHeight = height - labelPadding
    val cellWidth = gridWidth / 31f
    val cellHeight = gridHeight / 12f
    val radius = (min(cellWidth, cellHeight) / 2f) - 2f

    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 12f
        color = Color.WHITE
    }

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

    // Draw the grid of days
    var dayCounter = 0
    for (month in 0..11) { // 12 months (rows)
        val daysInCurrentMonth = daysInMonths[month]
        for (day in 1..daysInCurrentMonth) { // days in month (columns)
            dayCounter++

            paint.color = myTheme.select(dayCounter, dayOfYear).toArgb()

            val cx = labelPadding + (day - 1) * cellWidth + cellWidth / 2f
            val cy = labelPadding + month * cellHeight + cellHeight / 2f
            canvas.drawCircle(cx, cy, radius, paint)
        }
    }
    return bitmap
}
