package com.example.ui.audio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.runtime.collectAsState
import com.example.ui.MediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioScreen(
    viewModel: MediaViewModel,
    onNavigateToSettings: () -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("الأغاني", "الألبومات", "الفنانين", "قوائم التشغيل", "الأخيرة", "المفضلة", "المجلدات")
    val songs by viewModel.songs.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Player Music BR") },
            navigationIcon = {
                IconButton(onClick = { /* Open Profile */ }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                }
            },
            actions = {
                IconButton(onClick = onNavigateToSettings) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings")
                }
            }
        )
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 8.dp
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Tab Content
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTabIndex) {
                0 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        if (songs.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize().padding(32.dp),
                                    contentAlignment = androidx.compose.ui.Alignment.Center
                                ) {
                                    Text("No songs found visually.")
                                }
                            }
                        } else {
                            items(songs) { song ->
                                ListItem(
                                    headlineContent = { Text(song.title) },
                                    supportingContent = { Text("${song.artist} • ${song.album}") },
                                    leadingContent = {
                                        Box(
                                            modifier = Modifier.size(48.dp).background(androidx.compose.ui.graphics.Color.DarkGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                                            contentAlignment = androidx.compose.ui.Alignment.Center
                                        ) {
                                            Icon(Icons.Filled.Audiotrack, contentDescription = null)
                                        }
                                    },
                                    modifier = Modifier.clickable {
                                        viewModel.playbackManager.playSong(song)
                                    }
                                )
                            }
                        }
                    }
                }
                1 -> Text("Albums Grid goes here")
                2 -> Text("Artists Grid goes here")
                3 -> Text("Playlists List goes here")
                4 -> Text("Last Played Tracker goes here")
                5 -> Text("Favorites List goes here")
                6 -> Text("Custom Extra Tool goes here")
            }
        }
    }
}
