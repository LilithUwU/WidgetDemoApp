package com.hexagon.widgetdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hexagon.widgetdemoapp.ui.theme.WidgetDemoAppTheme
import com.hexagon.widgetdemoapp.widget.Day
import com.hexagon.widgetdemoapp.widget.MyBg
import com.hexagon.widgetdemoapp.widget.MyBox
import com.hexagon.widgetdemoapp.widget.MyTheme
import com.hexagon.widgetdemoapp.widget.getSettingsFlow
import com.hexagon.widgetdemoapp.widget.saveBackground
import com.hexagon.widgetdemoapp.widget.saveTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WidgetDemoAppTheme {
                Scaffold() { innerPadding ->
                    MyScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    WidgetDemoAppTheme {
        MyScreen()
    }
}

@Composable
fun MyScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val settings by getSettingsFlow(context).collectAsState(initial = MyBg.Gray to MyTheme.Blue)
    val scope = rememberCoroutineScope()

    val onBackgroundSelected: (MyBg) -> Unit = {
        scope.launch {
            saveBackground(context, it)
        }
    }

    val onIconThemeSelected: (MyTheme) -> Unit = { 
        scope.launch {
            saveTheme(context, it)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select background color", modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        ThemeSelector(
            selectedBackground = settings.first,
            onBackgroundSelected = onBackgroundSelected,
            selectedIconTheme = settings.second,
            onIconThemeSelected = onIconThemeSelected
        )
        PreviewWidget(
            selectedBackground = settings.first,
            selectedIconTheme = settings.second
        )
    }
}

@Composable
private fun PreviewWidget(
    selectedBackground: MyBg,
    selectedIconTheme: MyTheme
) {
    val bg = selectedBackground.color

    Box(
        modifier = Modifier
            .background(
                bg,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
    ) {
        Row {
            repeat(10) { Day(selectedIconTheme.past) }
            Day(selectedIconTheme.present)
            repeat(10) { Day(selectedIconTheme.future) }
        }
    }
}


@Composable
fun ThemeSelector(
    selectedBackground: MyBg,
    onBackgroundSelected: (MyBg) -> Unit,
    selectedIconTheme: MyTheme,
    onIconThemeSelected: (MyTheme) -> Unit
) {
    Text(
        "Select Background Color",
        style = MaterialTheme.typography.titleLarge,
    )

    BackgroundSelectorView(selectedBackground, onBackgroundSelected)

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        "Select icon theme (past, present, future)",
        style = MaterialTheme.typography.titleLarge,
    )

    ThemeSelectorView(selectedIconTheme, onIconThemeSelected)
}

@Composable
private fun ThemeSelectorView(
    selectedIconTheme: MyTheme,
    onIconThemeSelected: (MyTheme) -> Unit
) {
    MyTheme.entries.forEach { iconTheme ->
        val isSelected = (iconTheme == selectedIconTheme)
        Row(
            modifier = Modifier
                .padding(16.dp)
                .selectable(
                    selected = isSelected,
                    onClick = { onIconThemeSelected(iconTheme) }
                )
                .border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = if (isSelected) Color.LightGray else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyBox(iconTheme.past, isSelected)
            MyBox(iconTheme.present, isSelected)
            MyBox(iconTheme.future, isSelected)
        }
    }
}

@Composable
private fun BackgroundSelectorView(
    selectedBackground: MyBg,
    onBackgroundSelected: (MyBg) -> Unit
) {
    Row(modifier = Modifier.padding(16.dp)) {
        MyBg.entries.forEach { backgroundOption ->
            val isSelected = (backgroundOption == selectedBackground)
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundOption.color)
                    .selectable(
                        selected = isSelected,
                        onClick = { onBackgroundSelected(backgroundOption) }
                    )
                    .border(
                        width = if (isSelected) 3.dp else 0.dp,
                        color = if (isSelected) Color.LightGray else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
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
    }
}
