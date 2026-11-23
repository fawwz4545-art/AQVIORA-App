package com.example.advisoryapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
    val calcTabs = listOf("Kalkulator HPP Kuliner", "Laba Bersih", "Pajak UMKM 0.5%")

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
                        text = "Kalkulator Bisnis UMKM (Free Tools)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Hitung HPP porsi kuliner, modal, laba bersih & PPh UMKM gratis",
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
                // 1. KALKULATOR HPP KULINER & MODAL (SESUAI GAMBAR)
                0 -> HppCulinaryCalculator(currencyFormat, onNavigateToExperts)
                // 2. KALKULATOR LABA BERSIH
                1 -> NetProfitCalculator(currencyFormat, onNavigateToExperts)
                // 3. KALKULATOR PAJAK UMKM (PP 55/2022)
                2 -> UmkmTaxCalculator(currencyFormat, onNavigateToExperts)
            }
        }
    }
}

// 1. KALKULATOR HPP KULINER & MIE AYAM (SESUAI GAMBAR USER)
@Composable
fun HppCulinaryCalculator(
    currencyFormat: NumberFormat,
    onNavigateToExperts: () -> Unit
) {
    val priceOptions = listOf(10000.0, 12000.0, 15000.0, 18000.0)
    var selectedPrice by remember { mutableStateOf(12000.0) }

    var porsiSehariInput by remember { mutableStateOf("30") }
    var porsiPerKgAyamInput by remember { mutableStateOf("20") }

    val cabaiDaysOptions = listOf(1, 2, 3, 7)
    var selectedCabaiDays by remember { mutableIntStateOf(3) }

    var showResult by remember { mutableStateOf(false) }

    val porsiSehari = porsiSehariInput.toDoubleOrNull() ?: 0.0
    val porsiPerKgAyam = porsiPerKgAyamInput.toDoubleOrNull() ?: 1.0

    // Calculations
    val omzetHarian = selectedPrice * porsiSehari
    val omzetBulanan = omzetHarian * 30.0

    val biayaAyamHarian = (porsiSehari / porsiPerKgAyam) * 38000.0 // Estimasi Ayam Rp 38rb/kg
    val biayaCabaiHarian = 40000.0 / selectedCabaiDays
    val estimasiBiayaBahanHarian = biayaAyamHarian + biayaCabaiHarian + (porsiSehari * 2500.0) // Bumbu/Mie/Mie porsi

    val estimasiHppPerPorsi = if (porsiSehari > 0) estimasiBiayaBahanHarian / porsiSehari else 0.0
    val labaBersihHarian = omzetHarian - estimasiBiayaBahanHarian
    val labaBersihBulanan = labaBersihHarian * 30.0

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Q1: Harga jual per porsi
        Column {
            Text(
                text = "1. Harga jual mie ayam / produk per porsi",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            priceOptions.forEach { price ->
                val isSelected = selectedPrice == price
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { selectedPrice = price }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { selectedPrice = price },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = currencyFormat.format(price),
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Q2: Rata-rata porsi terjual sehari
        Column {
            Text(
                text = "2. Rata-rata porsi terjual sehari",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = porsiSehariInput,
                onValueChange = { porsiSehariInput = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("30") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Q3: Ayam 1 kg cukup untuk berapa porsi?
        Column {
            Text(
                text = "3. Ayam 1 kg cukup untuk berapa porsi?",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = porsiPerKgAyamInput,
                onValueChange = { porsiPerKgAyamInput = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("20") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Q4: Cabai Rp40.000 biasanya habis dalam berapa hari?
        Column {
            Text(
                text = "4. Cabai Rp40.000 biasanya habis dalam berapa hari?",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            cabaiDaysOptions.forEach { days ->
                val isSelected = selectedCabaiDays == days
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { selectedCabaiDays = days }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { selectedCabaiDays = days },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "$days hari",
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Button: Hitung modal dan keuntungan ->
        Button(
            onClick = { showResult = true },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Hitung modal dan keuntungan", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }

        // Result Card Output
        AnimatedVisibility(visible = showResult) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Hasil Simulasi Modal & Keuntungan UMKM Kuliner:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Estimasi Omzet Bulanan:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(currencyFormat.format(omzetBulanan), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Estimasi HPP Bahan per Porsi:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(currencyFormat.format(estimasiHppPerPorsi), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Estimasi Keuntungan Bersih Bulanan:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(currencyFormat.format(labaBersihBulanan), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onNavigateToExperts,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Konsultasi Efisiensi HPP dengan Expert", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
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
