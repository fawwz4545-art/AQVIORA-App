package com.example.advisoryapp.model

data class ActionItem(
    val id: String,
    val pillar: PillarType,
    val priority: String, // High, Medium, Low
    val title: String,
    val pic: String,
    val deadline: String,
    val isCompleted: Boolean = false
)

data class ClientProject(
    val id: String,
    val title: String,
    val pillar: PillarType,
    val clientName: String,
    val currentPhase: String, // e.g. "Phase 3: Implementation"
    val progressPercent: Float, // 0.0f - 1.0f
    val assignedExpert: String,
    val startDate: String,
    val targetEndDate: String,
    val status: String // Active, Proposal, Completed
)

data class DocumentItem(
    val id: String,
    val title: String,
    val category: String, // Report, SOP, Working Paper, Budget, Invoice
    val pillar: PillarType,
    val dateUploaded: String,
    val fileFormat: String, // PDF, XLSX, DOCX
    val fileSize: String
)

data class InvoiceItem(
    val id: String,
    val invoiceNumber: String,
    val serviceName: String,
    val amount: Long,
    val issueDate: String,
    val dueDate: String,
    val isPaid: Boolean
)
