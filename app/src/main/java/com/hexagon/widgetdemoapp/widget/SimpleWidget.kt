package com.hexagon.widgetdemoapp.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.hexagon.widgetdemoapp.R
import java.util.Calendar

private fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}

class SimpleWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            SimpleWidgetContent(LocalContext.current)
        }
    }


        @Composable
        private fun SimpleWidgetContent(context: Context) {
            val cal = Calendar.getInstance()
            val currentYear = cal.get(Calendar.YEAR)
            val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)
            val daysInYear = if (isLeapYear(currentYear)) 366 else 365

            val bitmap = remember(daysInYear, dayOfYear) {
                val size = 400
                val bitmap = createBitmap(size, size)
                val canvas = android.graphics.Canvas(bitmap)

                val columns = 28
                val cellSize = size / columns.toFloat()
                val paint = android.graphics.Paint()

                var counter = 0
                for (row in 0 until (daysInYear / columns + 1)) {
                    for (col in 0 until columns) {
                        if (counter < daysInYear) {
                            counter++
                            paint.color = when {
                                counter < dayOfYear -> android.graphics.Color.GRAY
                                counter == dayOfYear -> android.graphics.Color.GREEN
                                else -> android.graphics.Color.LTGRAY
                            }

                            val left = col * cellSize + 2f
                            val top = row * cellSize + 2f
                            canvas.drawRect(left, top, left + cellSize - 4f, top + cellSize - 4f,
                                paint
                            )
                        }
                    }
                }
                bitmap
            }

            Column(
                modifier = GlanceModifier.fillMaxSize().padding(8.dp),
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {
                Text(text = "$currentYear: $daysInYear days")

                Image(
                    provider = ImageProvider(bitmap),
                    contentDescription = "Year Grid",
                    modifier = GlanceModifier.fillMaxWidth().defaultWeight()
                )
            }
        }
}
