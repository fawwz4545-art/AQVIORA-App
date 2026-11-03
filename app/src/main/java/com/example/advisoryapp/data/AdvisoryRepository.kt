package com.example.advisoryapp.data

import android.content.Context
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
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdvisoryRepository(context: Context? = null) {

    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    private val dbHelper: AppDatabaseHelper? = context?.let { AppDatabaseHelper(it.applicationContext) }

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _registeredUsers = mutableListOf(
        UserProfile(
            id = "usr-1",
            fullName = "Budi Hendrawan",
            companyName = "PT Mandiri Sejahtera",
            email = "budi@mandirisejahtera.co.id",
            phone = "081234567890",
            industry = "Manufaktur & Distribusi"
        )
    )

    private val _currentUser = MutableStateFlow<UserProfile?>(null)
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
        // Save demo user into internal local SQLite database
        dbHelper?.insertUser(_registeredUsers.first())

        // Load experts from local SQLite database if available
        val localExperts = dbHelper?.getAllExperts()
        if (!localExperts.isNullOrEmpty()) {
            _experts.value = localExperts
        } else {
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
        }

        // Load free questions & bookings from local SQLite database if available
        val localQuestions = dbHelper?.getAllFreeQuestions()
        if (!localQuestions.isNullOrEmpty()) {
            _freeQuestions.value = localQuestions
        }

        val localBookings = dbHelper?.getAllBookings()
        if (!localBookings.isNullOrEmpty()) {
            _bookings.value = localBookings
        }

        loadDataForUser(_currentUser.value)
    }

    private fun loadDataForUser(user: UserProfile?) {
        if (user == null) {
            _healthResults.value = emptyList()
            _actionItems.value = emptyList()
            _projects.value = emptyList()
            _documents.value = emptyList()
            _invoices.value = emptyList()
            _bookings.value = dbHelper?.getAllBookings() ?: emptyList()
            return
        }

        if (user.id == "usr-1") {
            // Demo account: Load sample presentation dataset
            loadDemoDataset()
        } else {
            // Brand new registered account: Clear old demo cache!
            _healthResults.value = emptyList()
            _actionItems.value = emptyList()
            _projects.value = emptyList()
            _documents.value = emptyList()
            _invoices.value = emptyList()
            _bookings.value = dbHelper?.getAllBookings() ?: emptyList()

            // Fetch user specific data from Supabase
            fetchUserDataFromSupabase(user.id)
        }
    }

    private fun loadDemoDataset() {
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
            )
        )

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
            )
        )

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
            )
        )

        _documents.value = listOf(
            DocumentItem(
                id = "doc-1",
                title = "Laporan Business Health Check 2026 - Mandiri Sejahtera.pdf",
                category = "Report",
                pillar = PillarType.ACCOUNTING,
                dateUploaded = "10 Feb 2026",
                fileFormat = "PDF",
                fileSize = "2.4 MB"
            )
        )

        _invoices.value = listOf(
            InvoiceItem(
                id = "inv-1",
                invoiceNumber = "INV/2026/02/089",
                serviceName = "Paket Business Health Check & Diagnostic",
                amount = 2500000,
                issueDate = "05 Feb 2026",
                dueDate = "12 Feb 2026",
                isPaid = true
            )
        )
    }

    private fun fetchUserDataFromSupabase(userId: String) {
        repositoryScope.launch {
            try {
                // Fetch User Bookings from Supabase
                val jsonBookings = SupabaseClient.get("booking_requests", "user_id=eq.$userId")
                if (jsonBookings.length() > 0) {
                    val fetchedBookings = mutableListOf<BookingRequest>()
                    for (i in 0 until jsonBookings.length()) {
                        val obj = jsonBookings.getJSONObject(i)
                        fetchedBookings.add(
                            BookingRequest(
                                id = obj.optString("id", "bk-$i"),
                                expertId = obj.optString("expert_id", "exp-1"),
                                expertName = obj.optString("expert_name", "Expert Specialist"),
                                date = obj.optString("booking_date", "Hari ini"),
                                timeSlot = obj.optString("time_slot", "10:00 WIB"),
                                clientTopic = obj.optString("client_topic", "Konsultasi Advisory"),
                                durationMinutes = obj.optInt("duration_minutes", 60),
                                status = obj.optString("status", "Scheduled")
                            )
                        )
                    }
                    _bookings.value = fetchedBookings
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun syncWithSupabase() {
        repositoryScope.launch {
            try {
                // 1. Fetch Users from Supabase
                val jsonUsers = SupabaseClient.get("users")
                if (jsonUsers.length() > 0) {
                    for (i in 0 until jsonUsers.length()) {
                        val obj = jsonUsers.getJSONObject(i)
                        val fetchedUser = UserProfile(
                            id = obj.optString("id", "usr-$i"),
                            fullName = obj.optString("full_name", "Pengguna Advisory"),
                            companyName = obj.optString("company_name", "Perusahaan Anda"),
                            email = obj.optString("email", ""),
                            phone = obj.optString("phone", ""),
                            industry = obj.optString("industry", "Umum")
                        )
                        if (_registeredUsers.none { it.email.lowercase() == fetchedUser.email.lowercase() }) {
                            _registeredUsers.add(fetchedUser)
                            dbHelper?.insertUser(fetchedUser)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Auth Actions: Internal Local Database (SQLite) + Fallback

    suspend fun loginUserOnline(email: String, pass: String): Boolean = withContext(Dispatchers.IO) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank() || pass.isBlank()) return@withContext false

        // 1. First Check Internal Local SQLite Database
        val localUser = dbHelper?.getUserByEmail(trimmedEmail)
        if (localUser != null) {
            _currentUser.value = localUser
            _isLoggedIn.value = true
            loadDataForUser(localUser)
            return@withContext true
        }

        // 2. Memory check
        val memUser = _registeredUsers.find { it.email.equals(trimmedEmail, ignoreCase = true) }
        if (memUser != null) {
            _currentUser.value = memUser
            _isLoggedIn.value = true
            dbHelper?.insertUser(memUser)
            loadDataForUser(memUser)
            return@withContext true
        }

        // 3. Supabase Cloud Fallback
        try {
            val jsonUsers = SupabaseClient.get("users", "email=eq.$trimmedEmail")
            if (jsonUsers.length() > 0) {
                val userObj = jsonUsers.getJSONObject(0)
                val profile = UserProfile(
                    id = userObj.optString("id", "usr-${System.currentTimeMillis()}"),
                    fullName = userObj.optString("full_name", "Pengguna Advisory"),
                    companyName = userObj.optString("company_name", "Perusahaan Anda"),
                    email = userObj.optString("email", trimmedEmail),
                    phone = userObj.optString("phone", ""),
                    industry = userObj.optString("industry", "Umum")
                )
                _registeredUsers.add(profile)
                dbHelper?.insertUser(profile)
                _currentUser.value = profile
                _isLoggedIn.value = true
                loadDataForUser(profile)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun registerUser(
        fullName: String,
        companyName: String,
        email: String,
        phone: String,
        industry: String,
        businessScale: String = "Menengah (UKM)"
    ): Boolean {
        if (fullName.isNotBlank() && email.isNotBlank()) {
            val userId = "usr-${System.currentTimeMillis()}"
            val profile = UserProfile(
                id = userId,
                fullName = fullName,
                companyName = companyName.ifBlank { "Perusahaan Anda" },
                email = email.trim(),
                phone = phone,
                industry = industry.ifBlank { "Manufaktur & Jasa" },
                businessScale = businessScale.ifBlank { "Menengah (UKM)" }
            )
            _registeredUsers.add(profile)
            _currentUser.value = profile
            _isLoggedIn.value = true

            // Save to Internal Local SQLite Database
            dbHelper?.insertUser(profile)
            loadDataForUser(profile)

            // Sync user to Supabase Cloud asynchronously
            repositoryScope.launch {
                try {
                    val json = JSONObject()
                    json.put("id", userId)
                    json.put("full_name", fullName)
                    json.put("company_name", profile.companyName)
                    json.put("email", profile.email)
                    json.put("phone", phone)
                    json.put("industry", profile.industry)
                    json.put("business_scale", profile.businessScale)
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
        _currentUser.value = null
        loadDataForUser(null)
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

        val aiAnswer = generateAnswerFor(detectedCategory)
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

        // Save to Internal Local SQLite Database
        dbHelper?.insertFreeQuestion(newQ)

        // Post to Supabase Cloud
        repositoryScope.launch {
            try {
                val currentUserId = _currentUser.value?.id ?: "usr-1"
                val json = JSONObject()
                json.put("id", questionId)
                json.put("user_id", currentUserId)
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

    private fun generateAnswerFor(pillar: PillarType): String {
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

        // Save to Internal Local SQLite Database
        dbHelper?.insertBooking(newBooking)

        // Post booking to Supabase Cloud
        repositoryScope.launch {
            try {
                val currentUserId = _currentUser.value?.id ?: "usr-1"
                val json = JSONObject()
                json.put("id", bookingId)
                json.put("user_id", currentUserId)
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
