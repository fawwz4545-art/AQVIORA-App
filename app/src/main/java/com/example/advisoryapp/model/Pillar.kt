package com.example.advisoryapp.model

enum class PillarType(val displayName: String, val description: String, val exampleServices: String) {
    ACCOUNTING(
        displayName = "Accounting",
        description = "Pembukuan, Laporan Keuangan & Sistem Akuntansi",
        exampleServices = "Pembukuan rutin, laporan keuangan, closing bulanan/tahunan, sistem akuntansi, SOP akuntansi."
    ),
    TAX(
        displayName = "Tax",
        description = "Kepatuhan & Strategi Perpajakan Terintegrasi",
        exampleServices = "Tax compliance, tax review, tax health check, tax planning, tax advisory, tax risk assessment."
    ),
    FINANCE(
        displayName = "Finance",
        description = "Manajemen Keuangan & Modal Kerja",
        exampleServices = "Cash flow management, analisis rasio keuangan, working capital optimization, financial forecasting."
    ),
    COSTING(
        displayName = "Costing",
        description = "Perhitungan HPP & Kontrol Biaya Produksi",
        exampleServices = "HPP akurat, standard costing, cost driver analysis, cost control, pricing strategy."
    ),
    BUDGETING(
        displayName = "Budgeting",
        description = "Perencanaan Anggaran & Analisis Varians",
        exampleServices = "Sales budget, production budget, operating expense budget, cash budget, variance analysis."
    ),
    INTERNAL_CONTROL(
        displayName = "Internal Control",
        description = "SOP, Matriks Otorisasi & Pengendalian Internal",
        exampleServices = "Penyusunan SOP, approval matrix, segregation of duties (SoD), fraud prevention & control assessment."
    ),
    RISK_MANAGEMENT(
        displayName = "Risk Management",
        description = "Identifikasi & Mitigasi Risiko Bisnis",
        exampleServices = "Risk assessment, penyusunan risk register, risk matrix, mitigasi risiko, Key Risk Indicators (KRI)."
    ),
    INTERNAL_AUDIT(
        displayName = "Internal Audit",
        description = "Audit Berbasis Risiko & Assurance",
        exampleServices = "Audit plan, audit program, fieldwork & testing, working papers, audit reporting, follow-up temuan."
    )
}

data class PillarInfo(
    val type: PillarType,
    val totalProjects: Int = 0,
    val activeIssues: Int = 0
)
