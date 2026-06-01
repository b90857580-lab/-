package com.example.data

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val dataPath: String,
    val isVideo: Boolean = false
)

data class Album(
    val id: Long,
    val name: String,
    val artist: String,
    val albumArtUri: String?
)

data class Artist(
    val id: Long,
    val name: String,
    val trackCount: Int
)
