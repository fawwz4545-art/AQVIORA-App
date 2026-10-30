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

    val currentQuestions = remember(selectedCategory) {
        DiagnosticQuestionsData.allQuestions[selectedCategory] ?: DiagnosticQuestionsData.allQuestions["Menengah (UKM)"] ?: emptyList()
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
                        text = "Mulai Kuesioner Diagnostic (${currentQuestions.size} Soal)",
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
                                    Text("Mulai Kuesioner (${currentQuestions.size} Soal)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiary, fontSize = 12.sp)
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
