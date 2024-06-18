package com.example.sportsground.data

data class GroundModel(val name: String, val id: Int)

val GroundData: List<GroundModel> = listOf(
    GroundModel(name = "Football", id = 1),
    GroundModel(name = "Cricket", id = 2)
)