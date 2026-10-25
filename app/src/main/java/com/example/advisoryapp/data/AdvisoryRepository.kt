package com.example.advisoryapp.data

import com.example.advisoryapp.model.ActionItem
import com.example.advisoryapp.model.BookingRequest
import com.example.advisoryapp.model.ClientProject
import com.example.advisoryapp.model.DocumentItem
import com.example.advisoryapp.model.Expert
import com.example.advisoryapp.model.FreeQuestion
import com.example.advisoryapp.model.HealthResult
import com.example.advisoryapp.model.HealthStatus
import com.example.advisoryapp.model.InvoiceItem
import com.example.advisoryapp.model.PillarType
import com.example.advisoryapp.model.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdvisoryRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<UserProfile?>(
        UserProfile(
            id = "usr-1",
            fullName = "Budi Hendrawan",
            companyName = "PT Mandiri Sejahtera",
            email = "budi@mandirisejahtera.co.id",
            phone = "081234567890",
            industry = "Manufaktur & Distribusi"
        )
    )
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _freeQuestions = MutableStateFlow<List<FreeQuestion>>(emptyList())
    val freeQuestions: StateFlow<List<FreeQuestion>> = _freeQuestions.asStateFlow()

    private val _healthResults = MutableStateFlow<List<HealthResult>>(emptyList())
    val healthResults: StateFlow<List<HealthResult>> = _healthResults.asStateFlow()

    private val _experts = MutableStateFlow<List<Expert>>(emptyList())
    val experts: StateFlow<List<Expert>> = _experts.asStateFlow()

    private val _bookings = MutableStateFlow<List<BookingRequest>>(emptyList())
    val bookings: StateFlow<List<BookingRequest>> = _bookings.asStateFlow()

    private val _actionItems = MutableStateFlow<List<ActionItem>>(emptyList())
    val actionItems: StateFlow<List<ActionItem>> = _actionItems.asStateFlow()

    private val _projects = MutableStateFlow<List<ClientProject>>(emptyList())
    val projects: StateFlow<List<ClientProject>> = _projects.asStateFlow()

    private val _documents = MutableStateFlow<List<DocumentItem>>(emptyList())
    val documents: StateFlow<List<DocumentItem>> = _documents.asStateFlow()

    private val _invoices = MutableStateFlow<List<InvoiceItem>>(emptyList())
    val invoices: StateFlow<List<InvoiceItem>> = _invoices.asStateFlow()

    init {
        loadInitialData()
        syncWithSupabase()
    }

    private fun loadInitialData() {
        // Initial Free Q&A History
        _freeQuestions.value = listOf(
            FreeQuestion(
                id = "fq-1",
                timestamp = "Hari ini, 09:30",
                questionText = "Omzet bisnis restoran saya naik 25% tetapi laba bersih malah turun. Apa penyebab utamanya?",
                category = PillarType.COSTING,
                aiBasicAnswer = "Fenomena ini biasanya disebabkan oleh: 1) Kenaikan HPP/biaya bahan baku yang tidak diteruskan ke harga jual, 2) Biaya operasional (overhead) yang membengkak tanpa kontrol, atau 3) Porsi diskon/promo yang terlalu agresif. Disarankan melakukan Costing & Margin Analysis.",
                recommendedPillar = PillarType.COSTING,
                ctaText = "Butuh analisis HPP & margin restoran Anda? Konsultasikan dengan Costing Expert."
            ),
            FreeQuestion(
                id = "fq-2",
                timestamp = "Kemarin, 14:15",
                questionText = "Apakah transaksi jasa konsultasi dengan vendor perorangan terutang PPh 23 atau PPh 21?",
                category = PillarType.TAX,
                aiBasicAnswer = "Pembayaran jasa kepada Wajib Pajak Orang Pribadi dalam negeri dipotong PPh Pasal 21 (Bukan Pegawai). PPh Pasal 23 berlaku khusus untuk pemotongan jasa yang dibayarkan kepada Wajib Pajak Badan dalam negeri.",
                recommendedPillar = PillarType.TAX,
                ctaText = "Pastikan kepatuhan & potong pungut pajak bisnis Anda. Buat sesi Tax Review."
            )
        )

        // Initial Health Diagnostic Results
        _healthResults.value = listOf(
            HealthResult(
                pillar = PillarType.ACCOUNTING,
                status = HealthStatus.BASIC,
                score = 80,
                findingSummary = "Laporan keuangan rutin bulanan sudah tersedia, sistem pencatatan sudah terstruktur.",
                recommendedNextStep = "Penyempurnaan closing checklist bulanan agar laporan terbit H+5."
            ),
            HealthResult(
                pillar = PillarType.TAX,
                status = HealthStatus.IMPROVEMENT_REQUIRED,
                score = 60,
                findingSummary = "SPT Masa PPN dan PPh terlaporkan, tetapi belum ada rekonsiliasi berkala antara Omzet Akuntansi vs SPT PPN.",
                recommendedNextStep = "Lakukan Tax Review & Rekonsiliasi Ekualisasi Omzet dan Objek Pemotongan Pajak."
            ),
            HealthResult(
                pillar = PillarType.COSTING,
                status = HealthStatus.HIGH_ATTENTION,
                score = 40,
                findingSummary = "Perhitungan HPP masih berdasarkan estimasi persentase global, belum menggunakan Cost Driver per jenis produk.",
                recommendedNextStep = "Implementasi Standard Costing & Cost Driver Analysis per kategori produk."
            ),
            HealthResult(
                pillar = PillarType.INTERNAL_CONTROL,
                status = HealthStatus.HIGH_ATTENTION,
                score = 45,
                findingSummary = "Approval pembayaran pengeluaran di atas Rp 10 juta masih dilakukan via chat dan belum ada pemisahan tugas (SoD) staf keuangan.",
                recommendedNextStep = "Penyusunan SOP Keuangan & Matriks Otorisasi Pengeluaran Kas/Bank."
            ),
            HealthResult(
                pillar = PillarType.FINANCE,
                status = HealthStatus.IMPROVEMENT_REQUIRED,
                score = 65,
                findingSummary = "Cash flow harian tercatat tetapi belum ada cash flow forecast 13 minggu ke depan.",
                recommendedNextStep = "Penyusunan Rolling Cash Flow Forecast & Working Capital Management."
            ),
            HealthResult(
                pillar = PillarType.BUDGETING,
                status = HealthStatus.HIGH_ATTENTION,
                score = 35,
                findingSummary = "Perusahaan belum memiliki Anggaran Tahunan (Budget) resmi dan analisis varians biaya harian.",
                recommendedNextStep = "Penyusunan Master Budget Perusahaan (Sales, Expense, & Cash Budget)."
            ),
            HealthResult(
                pillar = PillarType.RISK_MANAGEMENT,
                status = HealthStatus.IMPROVEMENT_REQUIRED,
                score = 50,
                findingSummary = "Risiko operasional dan piutang tak tertagih belum terdokumentasi dalam Risk Register resmi.",
                recommendedNextStep = "Identifikasi risiko & pembuatan Risk Register & Mitigation Plan."
            ),
            HealthResult(
                pillar = PillarType.INTERNAL_AUDIT,
                status = HealthStatus.HIGH_ATTENTION,
                score = 30,
                findingSummary = "Belum pernah dilakukan audit internal independen atas operasional cabang dan ketaatan SOP.",
                recommendedNextStep = "Program Internal Audit as a Service (Audit Berbasis Risiko)."
            )
        )

        // Experts
        _experts.value = listOf(
            Expert(
                id = "exp-1",
                name = "Budi Santoso, SE, Ak, CA, BKP",
                title = "Senior Tax & Accounting Advisory Specialist",
                primaryPillar = PillarType.TAX,
                secondaryPillars = listOf(PillarType.ACCOUNTING, PillarType.FINANCE),
                yearsExperience = 15,
                rating = 4.9,
                reviewCount = 128,
                bio = "Mantan Auditor Big 4 & Konsultan Pajak Terdaftar (BKP C). Berpengalaman mendampingi 200+ perusahaan manufaktur, retail & jasa.",
                certifications = listOf("BKP C (Konsultan Pajak)", "CA (Chartered Accountant)", "Certified Tax Advisor"),
                hourlyRate = 500000.0
            ),
            Expert(
                id = "exp-2",
                name = "Siti Rahmawati, M.Ak, CMA",
                title = "Costing & Budgeting Advisory Specialist",
                primaryPillar = PillarType.COSTING,
                secondaryPillars = listOf(PillarType.BUDGETING, PillarType.FINANCE),
                yearsExperience = 12,
                rating = 4.85,
                reviewCount = 94,
                bio = "Praktisi Manajemen Biaya & Anggaran Sektor Manufaktur, Kuliner (F&B) dan Distribusi. Ahli penyusunan HPP & Variance Analysis.",
                certifications = listOf("CMA (Certified Management Accountant)", "Certified Budget Professional"),
                hourlyRate = 450000.0
            ),
            Expert(
                id = "exp-3",
                name = "Hendrik Wijaya, SE, CIA, CRMA",
                title = "Internal Control & Internal Audit Advisor",
                primaryPillar = PillarType.INTERNAL_CONTROL,
                secondaryPillars = listOf(PillarType.INTERNAL_AUDIT, PillarType.RISK_MANAGEMENT),
                yearsExperience = 16,
                rating = 4.95,
                reviewCount = 110,
                bio = "Spesialis Pengendalian Internal, Pencegahan Fraud, SOP & Internal Audit. Pengalaman memimpin tim audit di holding group ternama.",
                certifications = listOf("CIA (Certified Internal Auditor)", "CRMA (Risk Management Assurance)"),
                hourlyRate = 550000.0
            ),
            Expert(
                id = "exp-4",
                name = "Diana Putri, M.Sc, CFA",
                title = "Financial Analysis & Corporate Advisory",
                primaryPillar = PillarType.FINANCE,
                secondaryPillars = listOf(PillarType.BUDGETING, PillarType.RISK_MANAGEMENT),
                yearsExperience = 10,
                rating = 4.8,
                reviewCount = 76,
                bio = "Ahli Financial Advisory, Cash Flow Optimization, & Restrukturisasi Modal Kerja untuk UMKM dan Bisnis Skala Menengah.",
                certifications = listOf("CFA Charterholder", "Financial Modeling Specialist"),
                hourlyRate = 400000.0
            )
        )

        // Action Items
        _actionItems.value = listOf(
            ActionItem(
                id = "act-1",
                pillar = PillarType.COSTING,
                priority = "High",
                title = "Revisi metode kalkulasi HPP bahan baku & overhead pabrikasi",
                pic = "Staf Akuntansi (Rina)",
                deadline = "15 Mar 2026",
                isCompleted = false
            ),
            ActionItem(
                id = "act-2",
                pillar = PillarType.TAX,
                priority = "High",
                title = "Ekualisasi Omzet Laporan Keuangan vs SPT Masa PPN Q4",
                pic = "Tax Spv (Budi)",
                deadline = "20 Mar 2026",
                isCompleted = false
            ),
            ActionItem(
                id = "act-3",
                pillar = PillarType.INTERNAL_CONTROL,
                priority = "Medium",
                title = "Finalisasi Matriks Otorisasi Pengeluaran Kas & SOP Pembelian",
                pic = "Manager Ops (Dewi)",
                deadline = "25 Mar 2026",
                isCompleted = true
            ),
            ActionItem(
                id = "act-4",
                pillar = PillarType.BUDGETING,
                priority = "High",
                title = "Penyusunan Master Budget 2026 & Key Performance Indicators",
                pic = "Finance Manager (Eko)",
                deadline = "30 Mar 2026",
                isCompleted = false
            )
        )

        // Active Projects
        _projects.value = listOf(
            ClientProject(
                id = "prj-1",
                title = "Penataan Sistem HPP & Cost Control Manufaktur",
                pillar = PillarType.COSTING,
                clientName = "PT Mandiri Sejahtera",
                currentPhase = "Phase 3: Implementation & Standard Costing",
                progressPercent = 0.65f,
                assignedExpert = "Siti Rahmawati, M.Ak, CMA",
                startDate = "01 Feb 2026",
                targetEndDate = "15 Apr 2026",
                status = "Active"
            ),
            ClientProject(
                id = "prj-2",
                title = "Tax Health Check & Rekonsiliasi Pajak Tahunan",
                pillar = PillarType.TAX,
                clientName = "PT Mandiri Sejahtera",
                currentPhase = "Phase 2: Fieldwork & Tax Gap Assessment",
                progressPercent = 0.40f,
                assignedExpert = "Budi Santoso, SE, Ak, CA, BKP",
                startDate = "15 Feb 2026",
                targetEndDate = "30 Mar 2026",
                status = "Active"
            ),
            ClientProject(
                id = "prj-3",
                title = "Internal Audit as a Service Q1 2026",
                pillar = PillarType.INTERNAL_AUDIT,
                clientName = "PT Mandiri Sejahtera",
                currentPhase = "Proposal / Scope Review",
                progressPercent = 0.10f,
                assignedExpert = "Hendrik Wijaya, SE, CIA, CRMA",
                startDate = "01 Apr 2026",
                targetEndDate = "30 Jun 2026",
                status = "Proposal"
            )
        )

        // Documents
        _documents.value = listOf(
            DocumentItem(
                id = "doc-1",
                title = "Laporan Business Health Check 2026 - Mandiri Sejahtera.pdf",
                category = "Report",
                pillar = PillarType.ACCOUNTING,
                dateUploaded = "10 Feb 2026",
                fileFormat = "PDF",
                fileSize = "2.4 MB"
            ),
            DocumentItem(
                id = "doc-2",
                title = "SOP Pengeluaran Kas & Approval Matrix v1.2.docx",
                category = "SOP",
                pillar = PillarType.INTERNAL_CONTROL,
                dateUploaded = "20 Feb 2026",
                fileFormat = "DOCX",
                fileSize = "850 KB"
            ),
            DocumentItem(
                id = "doc-3",
                title = "Model Kalkulasi HPP & Cost Driver - Final.xlsx",
                category = "Working Paper",
                pillar = PillarType.COSTING,
                dateUploaded = "28 Feb 2026",
                fileFormat = "XLSX",
                fileSize = "1.8 MB"
            )
        )

        // Invoices
        _invoices.value = listOf(
            InvoiceItem(
                id = "inv-1",
                invoiceNumber = "INV/2026/02/089",
                serviceName = "Paket Business Health Check & Diagnostic",
                amount = 2500000,
                issueDate = "05 Feb 2026",
                dueDate = "12 Feb 2026",
                isPaid = true
            ),
            InvoiceItem(
                id = "inv-2",
                invoiceNumber = "INV/2026/02/112",
                serviceName = "Term 1: Project Penataan HPP & Cost Control",
                amount = 7500000,
                issueDate = "15 Feb 2026",
                dueDate = "22 Feb 2026",
                isPaid = true
            ),
            InvoiceItem(
                id = "inv-3",
                invoiceNumber = "INV/2026/03/014",
                serviceName = "Term 2: Project Tax Health Check & Advisory",
                amount = 5000000,
                issueDate = "01 Mar 2026",
                dueDate = "15 Mar 2026",
                isPaid = false
            )
        )
    }

    private fun syncWithSupabase() {
        repositoryScope.launch {
            try {
                // Sync Experts from Supabase
                val jsonExperts = SupabaseClient.get("experts")
                if (jsonExperts.length() > 0) {
                    val fetchedExperts = mutableListOf<Expert>()
                    for (i in 0 until jsonExperts.length()) {
                        val obj = jsonExperts.getJSONObject(i)
                        val pPillarStr = obj.optString("primary_pillar", "ACCOUNTING")
                        val primaryPillar = try { PillarType.valueOf(pPillarStr) } catch (e: Exception) { PillarType.ACCOUNTING }

                        val secStr = obj.optString("secondary_pillars", "")
                        val secPillars = secStr.split(",").mapNotNull {
                            try { PillarType.valueOf(it.trim()) } catch (e: Exception) { null }
                        }

                        val certsStr = obj.optString("certifications", "")
                        val certsList = certsStr.split(",").map { it.trim() }

                        fetchedExperts.add(
                            Expert(
                                id = obj.optString("id", "exp-$i"),
                                name = obj.optString("name", "Konsultan Expert"),
                                title = obj.optString("title", "Advisory Specialist"),
                                primaryPillar = primaryPillar,
                                secondaryPillars = secPillars,
                                yearsExperience = obj.optInt("years_experience", 10),
                                rating = obj.optDouble("rating", 5.0),
                                reviewCount = obj.optInt("review_count", 50),
                                bio = obj.optString("bio", ""),
                                certifications = certsList,
                                hourlyRate = obj.optDouble("hourly_rate", 500000.0),
                                isAvailable = obj.optBoolean("is_available", true)
                            )
                        )
                    }
                    _experts.value = fetchedExperts
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Auth Actions

    fun loginUser(email: String, pass: String): Boolean {
        if (email.isNotBlank() && pass.isNotBlank()) {
            _isLoggedIn.value = true
            if (_currentUser.value == null) {
                _currentUser.value = UserProfile(
                    id = "usr-1",
                    fullName = "Pengguna Advisory",
                    companyName = "PT Mandiri Sejahtera",
                    email = email,
                    phone = "081234567890",
                    industry = "Umum"
                )
            }
            return true
        }
        return false
    }

    fun registerUser(
        fullName: String,
        companyName: String,
        email: String,
        phone: String,
        industry: String
    ): Boolean {
        if (fullName.isNotBlank() && email.isNotBlank()) {
            val userId = "usr-${System.currentTimeMillis()}"
            val profile = UserProfile(
                id = userId,
                fullName = fullName,
                companyName = if (companyName.isBlank()) "Perusahaan Anda" else companyName,
                email = email,
                phone = phone,
                industry = if (industry.isBlank()) "Manufaktur & Jasa" else industry
            )
            _currentUser.value = profile
            _isLoggedIn.value = true

            // Sync user to Supabase
            repositoryScope.launch {
                try {
                    val json = JSONObject()
                    json.put("id", userId)
                    json.put("full_name", fullName)
                    json.put("company_name", profile.companyName)
                    json.put("email", email)
                    json.put("phone", phone)
                    json.put("industry", profile.industry)
                    SupabaseClient.post("users", json)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return true
        }
        return false
    }

    fun logout() {
        _isLoggedIn.value = false
    }

    // Interactive Actions

    fun askFreeQuestion(questionText: String): FreeQuestion {
        val lower = questionText.lowercase()
        val detectedCategory = when {
            lower.contains("pajak") || lower.contains("spt") || lower.contains("ppn") || lower.contains("pph") -> PillarType.TAX
            lower.contains("hpp") || lower.contains("biaya") || lower.contains("margin") || lower.contains("harga") -> PillarType.COSTING
            lower.contains("kas") || lower.contains("cash") || lower.contains("modal") -> PillarType.FINANCE
            lower.contains("budget") || lower.contains("anggaran") -> PillarType.BUDGETING
            lower.contains("sop") || lower.contains("approval") || lower.contains("curang") || lower.contains("fraud") -> PillarType.INTERNAL_CONTROL
            lower.contains("risiko") || lower.contains("risk") -> PillarType.RISK_MANAGEMENT
            lower.contains("audit") || lower.contains("pemeriksaan") -> PillarType.INTERNAL_AUDIT
            else -> PillarType.ACCOUNTING
        }

        val aiAnswer = generateAnswerFor(questionText, detectedCategory)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = dateFormat.format(Date())
        val questionId = "fq-${System.currentTimeMillis()}"

        val newQ = FreeQuestion(
            id = questionId,
            timestamp = "Hari ini, $currentTime",
            questionText = questionText,
            category = detectedCategory,
            aiBasicAnswer = aiAnswer,
            recommendedPillar = detectedCategory,
            ctaText = "Butuh pemecahan masalah ${detectedCategory.displayName} bisnis Anda lebih mendalam? Jadwalkan Konsultasi Expert."
        )

        _freeQuestions.value = listOf(newQ) + _freeQuestions.value

        // Post to Supabase
        repositoryScope.launch {
            try {
                val json = JSONObject()
                json.put("id", questionId)
                json.put("user_id", _currentUser.value?.id ?: "usr-1")
                json.put("question_text", questionText)
                json.put("category", detectedCategory.name)
                json.put("ai_basic_answer", aiAnswer)
                json.put("recommended_pillar", detectedCategory.name)
                json.put("cta_text", newQ.ctaText)
                SupabaseClient.post("free_questions", json)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return newQ
    }

    private fun generateAnswerFor(q: String, pillar: PillarType): String {
        return when (pillar) {
            PillarType.TAX -> "Pertanyaan Anda berkaitan dengan kepatuhan & risiko perpajakan. Secara umum, setiap transaksi wajib ditelaah dasar hukumnya, tarif yang berlaku, serta kesesuaian faktur/bukti potong dengan laporan keuangan."
            PillarType.COSTING -> "Untuk masalah biaya/HPP, langkah awal adalah memisahkan biaya langsung (Direct Material & Labor) dengan Biaya Overhead Pabrik (BOP), serta menentukan Cost Driver yang relevan per lini produk."
            PillarType.FINANCE -> "Manajemen arus kas membutuhkan pemisahan rekening operasional vs cadangan, serta pemantauan Cash Conversion Cycle (CCC) agar modal kerja tidak tertahan di piutang atau persediaan."
            PillarType.BUDGETING -> "Penyusunan anggaran yang efektif dimulai dari Proyeksi Penjualan, dilanjutkan Anggaran Produksi & Operasional, serta evaluasi bulanan melalui Variance Analysis (Actual vs Budget)."
            PillarType.INTERNAL_CONTROL -> "Pengendalian internal yang sehat memerlukan SOP tertulis yang jelas, Matriks Otorisasi berjenjang, dan Pemisahan Tugas (Segregation of Duties) antara pembuat, pemroses, dan pemberi persetujuan."
            PillarType.RISK_MANAGEMENT -> "Pengelolaan risiko dilakukan dengan menginventarisir kejadian risiko pada Risk Register, menilai Dampak (Impact) vs Probabilitas (Likelihood), serta menentukan Rencana Mitigasi."
            PillarType.INTERNAL_AUDIT -> "Internal audit berfungsi memastikan kepatuhan terhadap SOP dan efektifitas pengendalian. Audit berbasis risiko memprioritaskan area bernilai tinggi atau berisiko kebocoran besar."
            PillarType.ACCOUNTING -> "Pencatatan akuntansi yang akurat berpedoman pada standar akuntansi berlaku (SAK/EMKM). Pastikan kelengkapan dokumen pendukung (voucher/faktur) dan pengakuan jurnal yang konsisten."
        }
    }

    fun bookExpert(expertId: String, date: String, slot: String, topic: String): BookingRequest {
        val expert = _experts.value.find { it.id == expertId }
        val bookingId = "bk-${System.currentTimeMillis()}"
        val newBooking = BookingRequest(
            id = bookingId,
            expertId = expertId,
            expertName = expert?.name ?: "Expert Specialist",
            date = date,
            timeSlot = slot,
            clientTopic = topic
        )
        _bookings.value = listOf(newBooking) + _bookings.value

        // Post booking to Supabase
        repositoryScope.launch {
            try {
                val json = JSONObject()
                json.put("id", bookingId)
                json.put("user_id", _currentUser.value?.id ?: "usr-1")
                json.put("expert_id", expertId)
                json.put("expert_name", expert?.name ?: "Expert Specialist")
                json.put("booking_date", date)
                json.put("time_slot", slot)
                json.put("client_topic", topic)
                json.put("duration_minutes", 60)
                json.put("status", "Scheduled")
                SupabaseClient.post("booking_requests", json)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return newBooking
    }

    fun toggleActionItem(id: String) {
        _actionItems.value = _actionItems.value.map { item ->
            if (item.id == id) item.copy(isCompleted = !item.isCompleted) else item
        }
    }

    fun updateHealthScores(newResults: List<HealthResult>) {
        _healthResults.value = newResults
    }

    fun approveProposal(projectId: String) {
        _projects.value = _projects.value.map { prj ->
            if (prj.id == projectId) {
                prj.copy(status = "Active", currentPhase = "Phase 1: Kickoff & Data Collection", progressPercent = 0.15f)
            } else prj
        }
    }
}
