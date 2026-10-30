package com.example.advisoryapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advisoryapp.model.HealthResult
import com.example.advisoryapp.model.HealthStatus
import com.example.advisoryapp.model.PillarType
import com.example.advisoryapp.ui.AdvisoryViewModel
import com.example.advisoryapp.ui.components.HealthBadge

data class DiagnosticQuestionItem(
    val pillar: PillarType,
    val questionText: String,
    val options: List<Pair<String, HealthStatus>>
)

@Composable
fun DiagnosticScreen(
    viewModel: AdvisoryViewModel,
    onNavigateToExperts: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val healthResults by viewModel.healthResults.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Scorecard, 1: Diagnostic Form

    val categories = listOf("Mikro", "Kecil", "Menengah (UKM)", "Enterprise (Besar)")
    var selectedCategory by remember { mutableStateOf(currentUser?.businessScale ?: "Menengah (UKM)") }

    // Dynamic Questionnaire Extracted from AQVIORA_Business_Diagnostic_Questionnaire-1.docx
    val questionsMap = remember {
        mapOf(
            "Mikro" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1. Bagaimana cara Anda mencatat uang yang masuk dari penjualan dan uang keluar untuk usaha?",
                    options = listOf(
                        "Menggunakan aplikasi pencatatan keuangan" to HealthStatus.BASIC,
                        "Mencatat menggunakan Excel atau Google Sheets" to HealthStatus.BASIC,
                        "Mencatat secara manual di buku" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya mengingat jumlah uang yang masuk dan keluar" to HealthStatus.HIGH_ATTENTION,
                        "Tidak pernah mencatat pemasukan dan pengeluaran" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A2. Apakah Anda mengetahui berapa keuntungan bersih yang diperoleh usaha setiap bulan?",
                    options = listOf(
                        "Ya, mengetahui jumlah keuntungan berdasarkan perhitungan" to HealthStatus.BASIC,
                        "Mengetahui perkiraan keuntungan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak bisa membedakan uang modal dan keuntungan" to HealthStatus.HIGH_ATTENTION,
                        "Tidak mengetahui keuntungan usaha" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A3. Apakah uang pribadi dan uang usaha sudah dipisahkan?",
                    options = listOf(
                        "Selalu dipisahkan menggunakan rekening berbeda" to HealthStatus.BASIC,
                        "Dipisahkan, tetapi terkadang masih tercampur" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Masih menggunakan rekening yang sama / dikelola bersamaan" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1. Apakah Anda mengetahui kewajiban membayar dan melaporkan pajak usaha?",
                    options = listOf(
                        "Ya, saya memahami kewajiban pajak usaha" to HealthStatus.BASIC,
                        "Mengetahui ada kewajiban pajak tetapi belum paham perhitungan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengetahui apakah usaha saya punya kewajiban pajak" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F1. Apakah uang yang tersedia saat ini cukup untuk membayar kebutuhan usaha sehari-hari?",
                    options = listOf(
                        "Selalu cukup" to HealthStatus.BASIC,
                        "Biasanya cukup tetapi terkadang mengalami kekurangan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Harus menggunakan uang pribadi / meminjam untuk kebutuhan usaha" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.COSTING,
                    questionText = "C1. Bagaimana Anda menentukan harga jual produk atau jasa?",
                    options = listOf(
                        "Menghitung seluruh biaya dan menambahkan keuntungan" to HealthStatus.BASIC,
                        "Menghitung biaya bahan baku saja" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengikuti harga pesaing / menentukan berdasarkan perkiraan" to HealthStatus.HIGH_ATTENTION
                    )
                )
            ),
            "Kecil" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1. Bagaimana sistem pencatatan transaksi keuangan yang diterapkan dalam usaha Anda?",
                    options = listOf(
                        "Menggunakan software akuntansi / terintegrasi" to HealthStatus.BASIC,
                        "Menggunakan spreadsheet / manual buku" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki sistem pencatatan yang terstruktur" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A2. Seberapa rutin perusahaan menyusun laporan keuangan?",
                    options = listOf(
                        "Setiap bulan" to HealthStatus.BASIC,
                        "Setiap tiga bulan / enam bulan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya ketika dibutuhkan / belum pernah formal" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1. Seberapa rutin perusahaan melakukan rekonsiliasi omzet dengan pelaporan pajak?",
                    options = listOf(
                        "Setiap bulan sebelum pelaporan" to HealthStatus.BASIC,
                        "Setiap akhir tahun / SPT Tahunan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak pernah melakukan rekonsiliasi" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F1. Bagaimana perusahaan melakukan monitoring terhadap arus kas?",
                    options = listOf(
                        "Membuat laporan arus kas secara rutin / cash flow forecasting" to HealthStatus.BASIC,
                        "Memantau melalui spreadsheet / saldo bank saja" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak melakukan monitoring secara terstruktur" to HealthStatus.HIGH_ATTENTION
                    )
                )
            ),
            "Menengah (UKM)" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1. Apakah perusahaan telah menerapkan kebijakan akuntansi dan prosedur pencatatan transaksi secara formal?",
                    options = listOf(
                        "Ya, seluruhnya terdokumentasi dan diterapkan secara konsisten" to HealthStatus.BASIC,
                        "Sudah memiliki kebijakan, tetapi belum mencakup seluruh transaksi" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki kebijakan akuntansi formal / tergantung staf" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A2. Berapa lama waktu yang dibutuhkan perusahaan untuk menyelesaikan proses monthly financial closing?",
                    options = listOf(
                        "Maksimal 5 hari kerja" to HealthStatus.BASIC,
                        "6–10 hari kerja" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Lebih dari 15 hari kerja / belum terstandarisasi" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1. Apakah perusahaan telah memiliki Tax Compliance Management System yang terdokumentasi?",
                    options = listOf(
                        "Ya, terdapat SOP, tax calendar, dan penanggung jawab yang jelas" to HealthStatus.BASIC,
                        "Memiliki SOP tetapi belum mencakup seluruh kewajiban pajak" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki sistem kepatuhan pajak yang terstruktur" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.INTERNAL_CONTROL,
                    questionText = "IC1. Bagaimana prosedur persetujuan (approval) pengeluaran kas & bank?",
                    options = listOf(
                        "Ada SOP tertulis, Matriks Otorisasi & Pemisahan Tugas (SoD)" to HealthStatus.BASIC,
                        "Ada approval tetapi masih sering dilakukan lisan/WA" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Semua uang dipegang owner / tanpa kontrol otorisasi" to HealthStatus.HIGH_ATTENTION
                    )
                )
            ),
            "Enterprise (Besar)" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1. Bagaimana tingkat integrasi Financial Reporting System dengan sistem operasional perusahaan?",
                    options = listOf(
                        "Terintegrasi sepenuhnya melalui ERP dengan automated financial consolidation" to HealthStatus.BASIC,
                        "Terintegrasi secara parsial dengan beberapa proses manual" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki integrasi sistem yang memadai" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1. Bagaimana tingkat kematangan Tax Governance Framework perusahaan?",
                    options = listOf(
                        "Enterprise-wide tax governance terintegrasi dengan corporate governance" to HealthStatus.BASIC,
                        "Kebijakan perpajakan dikelola oleh departemen pajak" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki tax governance framework yang komprehensif" to HealthStatus.HIGH_ATTENTION
                    )
                )
            )
        )
    }

    val currentQuestions = questionsMap[selectedCategory] ?: questionsMap["Menengah (UKM)"]!!
    val userAnswers = remember { mutableStateMapOf<PillarType, HealthStatus>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Executive Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "BUSINESS DIAGNOSTIC & HEALTH CHECK",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Self Diagnostic 8 Pilar Usaha",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Kuesioner evaluasi tingkat kesehatan akuntansi, pajak, costing, dan kontrol internal bisnis Anda.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                    lineHeight = 16.sp
                )
            }
        }

        // Tab Navigation
        PrimaryScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            edgePadding = 16.dp
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "Scorecard Health Index",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp,
                        color = if (selectedTab == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = "Mulai Kuesioner Diagnostic",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp,
                        color = if (selectedTab == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }

        when (selectedTab) {
            // TAB 0: SCORECARD SUMMARY
            0 -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        val averageScore = if (healthResults.isNotEmpty()) healthResults.asSequence().map { it.score }.average().toInt() else 0
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "BUSINESS HEALTH INDEX",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.tertiary
                                        )
                                        Text(
                                            text = "Skor Kesehatan Bisnis",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
                                            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary), shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$averageScore",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                LinearProgressIndicator(
                                    progress = { averageScore / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    trackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                                )

                                Spacer(modifier = Modifier.height(14.dp))
                                Button(
                                    onClick = { selectedTab = 1 },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Quiz, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Mulai Self Diagnostic Kuesioner", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiary, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                    if (healthResults.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(54.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Assessment, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text("Belum Ada Hasil Diagnostic", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Klik tombol di atas untuk memulai kuesioner evaluasi skala usaha Anda.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    } else {
                        items(healthResults) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = item.pillar.displayName,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        HealthBadge(status = item.status)
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = item.findingSummary,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        lineHeight = 16.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Rekomendasi: ${item.recommendedNextStep}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // TAB 1: DIAGNOSTIC QUESTIONNAIRE FORM
            1 -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Column {
                            Text(
                                text = "Kategori Skala Bisnis:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(categories) { cat ->
                                    FilterChip(
                                        selected = selectedCategory == cat,
                                        onClick = { selectedCategory = cat },
                                        label = { Text(cat, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
                                        leadingIcon = if (selectedCategory == cat) {
                                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                        } else null,
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                                        )
                                    )
                                }
                            }
                        }
                    }

                    items(currentQuestions) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = item.pillar.displayName.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.questionText,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 19.sp
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                item.options.forEach { (optionText, status) ->
                                    val isSelected = userAnswers[item.pillar] == status
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { userAnswers[item.pillar] = status }
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = { userAnswers[item.pillar] = status },
                                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = optionText,
                                            fontSize = 12.sp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                val newResults = userAnswers.map { (pillar, status) ->
                                    val score = when (status) {
                                        HealthStatus.BASIC -> 85
                                        HealthStatus.IMPROVEMENT_REQUIRED -> 60
                                        HealthStatus.HIGH_ATTENTION -> 35
                                    }
                                    val summary = when (status) {
                                        HealthStatus.BASIC -> "Pencatatan & kepatuhan $selectedCategory pada pilar ${pillar.displayName} sudah terstruktur."
                                        HealthStatus.IMPROVEMENT_REQUIRED -> "Perlu peningkatan rekonsiliasi dan SOP pada pilar ${pillar.displayName}."
                                        HealthStatus.HIGH_ATTENTION -> "Membutuhkan perhatian tinggi & penataan ulang pada pilar ${pillar.displayName}."
                                    }
                                    HealthResult(
                                        pillar = pillar,
                                        status = status,
                                        score = score,
                                        findingSummary = summary,
                                        recommendedNextStep = "Konsultasikan dengan Expert ${pillar.displayName}."
                                    )
                                }
                                if (newResults.isNotEmpty()) {
                                    viewModel.updateHealthCheckResults(newResults)
                                    selectedTab = 0
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text("Proses Hasil Diagnostic", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
