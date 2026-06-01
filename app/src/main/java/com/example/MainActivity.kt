package com.example

import android.os.Bundle
import android.Manifest
import android.os.Build
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.data.MediaRepository
import com.example.player.PlaybackManager
import com.example.ui.MainScreen
import com.example.ui.MediaViewModel
import com.example.ui.MediaViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_VIDEO)
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val context = this.applicationContext
    val repository = MediaRepository(context)
    val playbackManager = PlaybackManager(context)
    
    setContent {
      MyApplicationTheme {
        val viewModel: MediaViewModel = viewModel(
            factory = MediaViewModelFactory(repository, playbackManager)
        )

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions -> 
                val allGranted = permissions.values.all { it }
                if (allGranted) {
                    viewModel.loadSongs()
                }
            }
        )

        LaunchedEffect(Unit) {
            if (permissionsToRequest.any { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }) {
                permissionLauncher.launch(permissionsToRequest)
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen(viewModel = viewModel)
        }
      }
    }
  }
}
