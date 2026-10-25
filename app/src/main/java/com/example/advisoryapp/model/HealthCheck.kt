package com.example.advisoryapp.model

enum class HealthStatus(val label: String, val description: String) {
    BASIC("Basic (Sehat)", "Kondisi dasar memenuhi standar minimal, pertahankan dan lakukan penyempurnaan."),
    IMPROVEMENT_REQUIRED("Improvement Required", "Perlu perbaikan dalam waktu dekat untuk mencegah kebocoran/risiko lebih lanjut."),
    HIGH_ATTENTION("High Attention", "Perhatian tinggi/kritis! Memerlukan penanganan segera dari expert.")
}

data class HealthQuestion(
    val id: String,
    val pillar: PillarType,
    val questionText: String,
    val optionBasic: String,
    val optionImprovement: String,
    val optionHighAttention: String
)

data class HealthResult(
    val pillar: PillarType,
    val status: HealthStatus,
    val score: Int, // 0-100
    val findingSummary: String,
    val recommendedNextStep: String
)
