package com.example.advisoryapp.model

data class FreeQuestion(
    val id: String,
    val timestamp: String,
    val questionText: String,
    val category: PillarType?,
    val aiBasicAnswer: String,
    val recommendedPillar: PillarType,
    val ctaText: String
)
