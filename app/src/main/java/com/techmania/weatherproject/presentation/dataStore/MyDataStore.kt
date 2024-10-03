package com.techmania.weatherproject.presentation.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object MyDataStore {
    suspend fun save(key: String, value: String, context: Context) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun load(key: String, context: Context): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()[dataStoreKey]
        return preferences
    }

    fun loadFlow(key: String, context: Context): Flow<String> {
        val dataStoreKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[dataStoreKey] ?: "true"
        }
    }

    suspend fun ensureInitialized(key: String, defaultValue: String, context: Context) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            if (settings[dataStoreKey] == null) {
                settings[dataStoreKey] = defaultValue
            }
        }
    }
}