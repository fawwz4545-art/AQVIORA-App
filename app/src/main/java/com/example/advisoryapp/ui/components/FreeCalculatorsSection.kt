package com.example.advisoryapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun FreeCalculatorsSection(
    onNavigateToExperts: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCalcTab by remember { mutableIntStateOf(0) }
    val calcTabs = listOf("Laba Bersih", "Pajak UMKM 0.5%", "HPP & Biaya Produksi")

    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.getDefault()) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Kalkulator Bisnis Gratis (Free Tools)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Hitung laba bersih, PPh UMKM 0,5%, dan HPP per porsi secara gratis",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            PrimaryScrollableTabRow(
                selectedTabIndex = selectedCalcTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                edgePadding = 4.dp
            ) {
                calcTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedCalcTab == index,
                        onClick = { selectedCalcTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedCalcTab == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedCalcTab == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (selectedCalcTab) {
                // 1. KALKULATOR LABA BERSIH
                0 -> NetProfitCalculator(currencyFormat, onNavigateToExperts)
                // 2. KALKULATOR PAJAK UMKM (PP 55/2022)
                1 -> UmkmTaxCalculator(currencyFormat, onNavigateToExperts)
                // 3. KALKULATOR HPP & BIAYA PRODUKSI
                2 -> HppProductionCalculator(currencyFormat, onNavigateToExperts)
            }
        }
    }
}

@Composable
fun NetProfitCalculator(
    currencyFormat: NumberFormat,
    onNavigateToExperts: () -> Unit
) {
    var omzetInput by remember { mutableStateOf("50000000") }
    var hppInput by remember { mutableStateOf("30000000") }
    var opexInput by remember { mutableStateOf("8000000") }

    val omzet = omzetInput.toDoubleOrNull() ?: 0.0
    val hpp = hppInput.toDoubleOrNull() ?: 0.0
    val opex = opexInput.toDoubleOrNull() ?: 0.0

    val labaKotor = omzet - hpp
    val labaBersih = labaKotor - opex
    val marginBersih = if (omzet > 0) (labaBersih / omzet) * 100 else 0.0

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = omzetInput,
            onValueChange = { omzetInput = it },
            label = { Text("Omzet Penjualan Bulanan (Rp)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )
        OutlinedTextField(
            value = hppInput,
            onValueChange = { hppInput = it },
            label = { Text("HPP / Biaya Bahan Baku (Rp)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )
        OutlinedTextField(
            value = opexInput,
            onValueChange = { opexInput = it },
            label = { Text("Biaya Operasional / Gaji / Sewa (Rp)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Laba Kotor (Gross Profit):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(currencyFormat.format(labaKotor), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Laba Bersih (Net Profit):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(currencyFormat.format(labaBersih), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Margin Laba Bersih:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${String.format(Locale.getDefault(), "%.1f", marginBersih)}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }

        Button(
            onClick = onNavigateToExperts,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Konsultasikan Efisiensi Laba Bersih", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun UmkmTaxCalculator(
    currencyFormat: NumberFormat,
    onNavigateToExperts: () -> Unit
) {
    var omzetBulananInput by remember { mutableStateOf("30000000") }
    val omzetBulanan = omzetBulananInput.toDoubleOrNull() ?: 0.0
    val pphFinal05 = omzetBulanan * 0.005

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = omzetBulananInput,
            onValueChange = { omzetBulananInput = it },
            label = { Text("Omzet Penjualan Bulanan (Rp)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Simulasi PPh Final UMKM (PP 55/2022):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tarif Pajak Final:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("0,5% dari Omzet", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("PPh Final Terutang Bulanan:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(currencyFormat.format(pphFinal05), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "💡 Bebas PPh untuk WPOP UMKM dengan omzet kumulatif < Rp 500 Juta per tahun.",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Button(
            onClick = onNavigateToExperts,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Jadwalkan Tax Review & Perencanaan Pajak", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HppProductionCalculator(
    currencyFormat: NumberFormat,
    onNavigateToExperts: () -> Unit
) {
    var bahanBakuInput by remember { mutableStateOf("1500000") }
    var tenagaKerjaInput by remember { mutableStateOf("500000") }
    var bopInput by remember { mutableStateOf("300000") }
    var porsiInput by remember { mutableStateOf("100") }

    val bahan = bahanBakuInput.toDoubleOrNull() ?: 0.0
    val tenaga = tenagaKerjaInput.toDoubleOrNull() ?: 0.0
    val bop = bopInput.toDoubleOrNull() ?: 0.0
    val porsi = porsiInput.toIntOrNull() ?: 1

    val totalBiaya = bahan + tenaga + bop
    val hppPerUnit = if (porsi > 0) totalBiaya / porsi else 0.0
    val saranHargaMargin30 = hppPerUnit / 0.7
    val saranHargaMargin50 = hppPerUnit / 0.5

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = bahanBakuInput,
                onValueChange = { bahanBakuInput = it },
                label = { Text("Biaya Bahan (Rp)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = tenagaKerjaInput,
                onValueChange = { tenagaKerjaInput = it },
                label = { Text("Tenaga Kerja (Rp)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = bopInput,
                onValueChange = { bopInput = it },
                label = { Text("BOP / Kemasan (Rp)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            )
            OutlinedTextField(
                value = porsiInput,
                onValueChange = { porsiInput = it },
                label = { Text("Jumlah Unit") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Biaya Produksi:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(currencyFormat.format(totalBiaya), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("HPP per Unit / Porsi:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(currencyFormat.format(hppPerUnit), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Saran Harga Jual (Margin 30%):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(currencyFormat.format(saranHargaMargin30), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Saran Harga Jual (Margin 50%):", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(currencyFormat.format(saranHargaMargin50), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }

        Button(
            onClick = onNavigateToExperts,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Konsultasikan Kalkulasi HPP & Standard Costing", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}
