package com.example.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    var crossfadeEnabled by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ListItem(
                headlineContent = { Text("Languages") },
                supportingContent = { Text("Choose from 90 specific languages or System") },
                leadingContent = { Icon(Icons.Filled.Language, contentDescription = null) },
                modifier = Modifier.clickable { /* Show Language Dialog */ }
            )
            
            ListItem(
                headlineContent = { Text("Theme Customization") },
                supportingContent = { Text("System, Light, Dark") },
                leadingContent = { Icon(Icons.Filled.Palette, contentDescription = null) },
                modifier = Modifier.clickable { /* Show Theme Dialog */ }
            )

            ListItem(
                headlineContent = { Text("Audio Crossfade") },
                supportingContent = { Text("Smoothly fade between tracks") },
                leadingContent = { Icon(Icons.Filled.Timelapse, contentDescription = null) },
                trailingContent = { 
                    Switch(
                        checked = crossfadeEnabled,
                        onCheckedChange = { crossfadeEnabled = it }
                    ) 
                }
            )

            ListItem(
                headlineContent = { Text("Advanced Audio & Video Tweaks") },
                supportingContent = { Text("Hundreds of extra configurations") },
                leadingContent = { Icon(Icons.Filled.Tune, contentDescription = null) },
                modifier = Modifier.clickable { /* Show Advanced Tweaks */ }
            )
        }
    }
}
