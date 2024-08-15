package com.example.sportsground.data

data class GroundModel(val name: String, val id: Int)

val GroundData: List<GroundModel> = listOf(
    GroundModel(name = "Football", id = 1),
    GroundModel(name = "Cricket", id = 2),
    GroundModel(name = "Parallax 1", id = 3),
    GroundModel(name = "Parallax 2", id = 4),
    GroundModel(name = "Parallax 3", id = 5),
)