package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.audio.AudioScreen
import com.example.ui.video.VideoScreen
import com.example.ui.download.DownloadScreen
import com.example.ui.settings.SettingsScreen

import androidx.compose.runtime.collectAsState

@Composable
fun MainScreen(viewModel: MediaViewModel) {
    val navController = rememberNavController()
    val items = listOf("الموسيقى", "الفيديوهات", "التنزيل")
    val icons = listOf(Icons.Filled.Audiotrack, Icons.Filled.VideoLibrary, Icons.Filled.Download)
    val routes = listOf("audio", "video", "download")

    val currentSong by viewModel.playbackManager.currentSong.collectAsState()
    val isPlaying by viewModel.playbackManager.isPlaying.collectAsState()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            
            Column {
                MiniPlayer(
                    song = currentSong,
                    isPlaying = isPlaying,
                    onPlayPause = { viewModel.playbackManager.togglePlayPause() }
                )
                
                // Only show bottom bar on main screens
                if (currentRoute in routes) {
                    NavigationBar {
                        routes.forEachIndexed { index, route ->
                            NavigationBarItem(
                                icon = { Icon(icons[index], contentDescription = items[index]) },
                                label = { Text(items[index]) },
                                selected = currentRoute == route,
                                onClick = { 
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            NavHost(navController, startDestination = "audio") {
                composable("audio") { 
                    AudioScreen(
                        viewModel = viewModel,
                        onNavigateToSettings = { navController.navigate("settings") }
                    ) 
                }
                composable("video") { VideoScreen() }
                composable("download") { DownloadScreen() }
                composable("settings") { 
                    SettingsScreen(onNavigateBack = { navController.popBackStack() }) 
                }
            }
        }
    }
}
