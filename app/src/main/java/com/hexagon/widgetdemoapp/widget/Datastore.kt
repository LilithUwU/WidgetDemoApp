package com.hexagon.widgetdemoapp.widget

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object AppSettings {
    val SELECTED_BACKGROUND = stringPreferencesKey("selected_background")
    val SELECTED_THEME = stringPreferencesKey("selected_theme")
}

fun getBgFlow(context: Context): Flow<MyBg> {
    return context.dataStore.data.map { preferences ->
        MyBg.valueOf(preferences[AppSettings.SELECTED_BACKGROUND] ?: MyBg.Gray.name)
    }
}

fun getThemeFlow(context: Context): Flow<MyTheme> {
    return context.dataStore.data.map { preferences ->
        MyTheme.valueOf(preferences[AppSettings.SELECTED_THEME] ?: MyTheme.Blue.name)
    }
}

suspend fun saveBackground(context: Context, background: MyBg) {
    context.dataStore.edit {
        it[AppSettings.SELECTED_BACKGROUND] = background.name
    }
}

suspend fun saveTheme(context: Context, theme: MyTheme) {
    context.dataStore.edit {
        it[AppSettings.SELECTED_THEME] = theme.name
    }
}
