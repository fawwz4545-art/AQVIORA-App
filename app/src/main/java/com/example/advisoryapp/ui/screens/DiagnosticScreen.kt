package com.example.advisoryapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.advisoryapp.model.HealthStatus
import com.example.advisoryapp.model.PillarType
import com.example.advisoryapp.ui.AdvisoryViewModel
import com.example.advisoryapp.ui.components.HealthBadge

@Composable
fun DiagnosticScreen(
    viewModel: AdvisoryViewModel,
    onNavigateToExperts: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val healthResults by viewModel.healthResults.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Scorecard, 1: Diagnostic Form

    val diagnosticQuestions = remember {
        listOf(
            Triple(
                PillarType.ACCOUNTING,
                "Bagaimana status pembukuan & laporan keuangan bulanan bisnis Anda?",
                listOf(
                    "Laporan keuangan bulanan terbit rutin < H+10" to HealthStatus.BASIC,
                    "Pembukuan ada tetapi sering terlambat/belum rapi" to HealthStatus.IMPROVEMENT_REQUIRED,
                    "Tidak ada pembukuan resmi / mencatat seadanya di nota" to HealthStatus.HIGH_ATTENTION
                )
            ),
            Triple(
                PillarType.TAX,
                "Apakah perusahaan rutin melakukan ekualisasi omzet vs SPT PPN & PPh?",
                listOf(
                    "Rutin rekonsiliasi bulanan & SPT terlaporkan tepat waktu" to HealthStatus.BASIC,
                    "SPT terlaporkan tetapi tidak pernah dilakukan ekualisasi" to HealthStatus.IMPROVEMENT_REQUIRED,
                    "Pernah kena denda/SP2DK dan belum memiliki review pajak" to HealthStatus.HIGH_ATTENTION
                )
            ),
            Triple(
                PillarType.COSTING,
                "Bagaimana perhitungan HPP & biaya produksi di tempat Anda?",
                listOf(
                    "Standard Costing akurat berdasarkan Cost Driver & BOP" to HealthStatus.BASIC,
                    "HPP dihitung persentase kasar tanpa detail overhead" to HealthStatus.IMPROVEMENT_REQUIRED,
                    "Tidak tahu persis HPP per unit produk/jasa" to HealthStatus.HIGH_ATTENTION
                )
            ),
            Triple(
                PillarType.INTERNAL_CONTROL,
                "Bagaimana prosedur persetujuan (approval) pengeluaran kas & bank?",
                listOf(
                    "Ada SOP tertulis, Matriks Otorisasi & Pemisahan Tugas (SoD)" to HealthStatus.BASIC,
                    "Ada approval tetapi masih sering dilakukan lisan/WA" to HealthStatus.IMPROVEMENT_REQUIRED,
                    "Semua uang dipegang owner / tanpa kontrol otorisasi" to HealthStatus.HIGH_ATTENTION
                )
            )
        )
    }

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
                    text = "Evaluasi Kesehatan & Kesiapan Bisnis",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // Tab Row
        PrimaryScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            edgePadding = 16.dp
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Assessment, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Health Scorecard", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Self Diagnostic Form", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            )
        }

        if (selectedTab == 0) {
            // SCORECARD VIEW
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
                                        color = MaterialTheme.colorScheme.tertiary,
                                        letterSpacing = 1.sp
                                    )
                                    Text(
                                        text = "Indeks Kesehatan Keseluruhan",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$averageScore%",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.tertiary
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

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Laporan ini memetakan gap dan area prioritas perbaikan pada 8 Pilar Layanan.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Status Per Area / Pilar Layanan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(healthResults) { result ->
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
                                    text = result.pillar.displayName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                HealthBadge(status = result.status)
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Temuan Assessment:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = result.findingSummary,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Rekomendasi Langkah Selanjutnya:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = result.recommendedNextStep,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = onNavigateToExperts,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Konsultasi Expert ${result.pillar.displayName}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }
            }
        } else {
            // DIAGNOSTIC FORM VIEW
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column {
                        Text(
                            text = "Business Diagnostic Questionnaire",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Jawab pertanyaan di bawah ini untuk memperbarui Business Health Index:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                items(diagnosticQuestions) { (pillar, question, options) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = pillar.displayName.uppercase(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = question,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            options.forEach { (optionText, status) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { userAnswers[pillar] = status }
                                        .padding(vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = userAnswers[pillar] == status,
                                        onClick = { userAnswers[pillar] = status },
                                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = optionText,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium,
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
                            val updated = healthResults.map { current ->
                                val userStatus = userAnswers[current.pillar]
                                if (userStatus != null) {
                                    val newScore = when (userStatus) {
                                        HealthStatus.BASIC -> 85
                                        HealthStatus.IMPROVEMENT_REQUIRED -> 55
                                        HealthStatus.HIGH_ATTENTION -> 35
                                    }
                                    current.copy(status = userStatus, score = newScore)
                                } else current
                            }
                            viewModel.updateHealthCheckResults(updated)
                            selectedTab = 0
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Simpan & Hitung Ulang Health Index", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}
