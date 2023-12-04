package com.example.project.api

data class WeatherResponse(
    val main: Main,
    val coord: Coord,
)

data class Main(
    val temp: Double
)

data class Coord(
    val lat: Double,
    val lon: Double
)
