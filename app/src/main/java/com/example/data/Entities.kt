package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val coverPath: String? = null
)

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mediaUrl: String,
    val title: String,
    val artist: String,
    val isVideo: Boolean = false
)

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val profileImagePath: String?,
    val selectedLanguage: String = "en",
    val themeChoice: Int = 0, // 0 = System, 1 = Light, 2 = Dark
    val crossfadeEnabled: Boolean = false
)
