package com.example.advisoryapp.model

data class Expert(
    val id: String,
    val name: String,
    val title: String,
    val primaryPillar: PillarType,
    val secondaryPillars: List<PillarType>,
    val yearsExperience: Int,
    val rating: Double,
    val reviewCount: Int,
    val bio: String,
    val certifications: List<String>,
    val hourlyRate: Double,
    val isAvailable: Boolean = true
)

data class BookingRequest(
    val id: String,
    val expertId: String,
    val expertName: String,
    val date: String,
    val timeSlot: String,
    val clientTopic: String,
    val durationMinutes: Int = 60,
    val status: String = "Scheduled"
)
