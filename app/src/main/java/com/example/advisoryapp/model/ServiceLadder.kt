package com.example.advisoryapp.model

enum class ServiceLadderLevel(
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val deliverables: String,
    val priceLabel: String
) {
    ASK_FREE(
        levelNumber = 1,
        title = "ASK (Free Ask)",
        subtitle = "Tanya Gratis Masalah Bisnis",
        deliverables = "Jawaban dasar, edukasi awal, FAQ, dan rekomendasi langkah berikutnya.",
        priceLabel = "GRATIS"
    ),
    CONSULT(
        levelNumber = 2,
        title = "CONSULT",
        subtitle = "Konsultasi Expert 1-on-1",
        deliverables = "Sesi 30/60 menit dengan Expert, identifikasi masalah utama & poin aksi.",
        priceLabel = "Rp 350.000 / sesi"
    ),
    DIAGNOSE(
        levelNumber = 3,
        title = "DIAGNOSE",
        subtitle = "Health Check & Diagnostic",
        deliverables = "Laporan diagnosa komprehensif, gap assessment, & roadmap perbaikan.",
        priceLabel = "Rp 2.500.000 / paket"
    ),
    SOLUTION(
        levelNumber = 4,
        title = "SOLUTION",
        subtitle = "Service Proposal & Solution",
        deliverables = "Desain solusi spesifik per pilar, scope of work, & rancangan deliverables.",
        priceLabel = "Berdasarkan Scope"
    ),
    IMPLEMENT(
        levelNumber = 5,
        title = "IMPLEMENT",
        subtitle = "Eksekusi System & SOP",
        deliverables = "Implementasi sistem akuntansi, penyusunan SOP, budgeting, & kontrol internal.",
        priceLabel = "Project Based"
    ),
    MONITOR(
        levelNumber = 6,
        title = "MONITOR & GROW",
        subtitle = "Retainer & Internal Audit",
        deliverables = "Review rutin bulanan/triwulanan, Internal Audit as a Service, & advisory berkelanjutan.",
        priceLabel = "Rp 5.000.000 / bln"
    )
}
