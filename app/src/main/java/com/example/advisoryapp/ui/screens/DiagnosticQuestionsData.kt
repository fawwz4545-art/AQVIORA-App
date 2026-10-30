package com.example.advisoryapp.ui.screens

import com.example.advisoryapp.model.HealthStatus
import com.example.advisoryapp.model.PillarType

object DiagnosticQuestionsData {

    val allQuestions: Map<String, List<DiagnosticQuestionItem>> by lazy {
        mapOf(
            "Mikro" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A.  Business Profile",
                    options = listOf(
                        "No." to HealthStatus.BASIC,
                        "Informasi" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Jenis Jawaban" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "1" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Nama usaha/perusahaan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Input teks" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "2" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kategori bisnis" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mikro / Kecil / Menengah / Enterprise" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "3" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Bidang usaha" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dagang / Manufaktur / Jasa / Lainnya" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "4" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Lama menjalankan usaha" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "<1 tahun / 1–3 tahun / 3–5 tahun / >5 tahun" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "5" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Omzet tahunan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Input nominal" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "6" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Jumlah karyawan" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Input angka" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "7" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Jumlah cabang/lokasi operasional" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Input angka" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "8" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Apakah sudah memiliki staf keuangan atau akuntansi?" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Ya / Tidak" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "9" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Apakah menggunakan software akuntansi?" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Ya / Tidak" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "10" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Apa tujuan utama menggunakan AQVIORA?" to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Accounting / Tax / Finance / Costing / Budgeting / Internal Control / Risk / Internal Audit" to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1.  Bagaimana cara Anda mencatat uang yang masuk dari hasil penjualan dan uang yang keluar untuk kebutuhan usaha?",
                    options = listOf(
                        "Tidak pernah mencatat pemasukan dan pengeluaran." to HealthStatus.BASIC,
                        "Hanya mengingat jumlah uang yang masuk dan keluar." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mencatat secara manual di buku." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mencatat menggunakan Excel atau Google Sheets." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan aplikasi pencatatan keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Meminta bantuan orang lain untuk mencatat keuangan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A2.  Apakah Anda mengetahui berapa keuntungan bersih yang diperoleh usaha setiap bulan?",
                    options = listOf(
                        "Ya, mengetahui jumlah keuntungan berdasarkan perhitungan." to HealthStatus.BASIC,
                        "Mengetahui perkiraan keuntungan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya mengetahui jumlah uang yang tersisa." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak bisa membedakan uang modal dan keuntungan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengetahui keuntungan usaha." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A3.  Apakah uang pribadi dan uang usaha sudah dipisahkan?",
                    options = listOf(
                        "Selalu dipisahkan menggunakan rekening berbeda." to HealthStatus.BASIC,
                        "Dipisahkan, tetapi terkadang masih tercampur." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Masih menggunakan rekening yang sama." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Seluruh uang usaha dan pribadi dikelola secara bersamaan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A4.  Apa kesulitan terbesar yang Anda alami dalam mengelola keuangan usaha?",
                    options = listOf(
                        "Sering lupa mencatat transaksi." to HealthStatus.BASIC,
                        "Tidak mengetahui keuntungan sebenarnya." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kesulitan mengetahui jumlah utang dan piutang." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengetahui jumlah modal yang masih tersedia." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kesulitan mengatur uang untuk kebutuhan operasional." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengalami kesulitan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "A4.  Bagaimana perusahaan mengevaluasi efektivitas Accounting Information System?",
                    options = listOf(
                        "Melalui periodic system audit, performance assessment, dan IT control evaluation." to HealthStatus.BASIC,
                        "Melalui evaluasi berkala oleh manajemen." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Melalui evaluasi berdasarkan keluhan pengguna." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Evaluasi dilakukan ketika terjadi gangguan sistem." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme evaluasi formal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1.  Apakah Anda mengetahui apakah usaha yang dijalankan memiliki kewajiban membayar dan melaporkan pajak?",
                    options = listOf(
                        "Ya, saya memahami kewajiban pajak usaha." to HealthStatus.BASIC,
                        "Mengetahui ada kewajiban pajak tetapi belum memahami perhitungannya." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pernah mendengar tentang pajak UMKM." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengetahui apakah usaha saya memiliki kewajiban pajak." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T2.  Bagaimana Anda menghitung pajak yang harus dibayar?",
                    options = listOf(
                        "Menghitung sendiri berdasarkan omzet." to HealthStatus.BASIC,
                        "Menggunakan aplikasi perpajakan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Meminta bantuan orang yang memahami pajak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan jasa konsultan pajak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak pernah menghitung pajak usaha." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T3.  Apakah Anda menyimpan bukti transaksi penjualan dan pembelian?",
                    options = listOf(
                        "Selalu menyimpan seluruh bukti transaksi." to HealthStatus.BASIC,
                        "Menyimpan sebagian besar bukti transaksi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya menyimpan transaksi dalam jumlah besar." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Bukti transaksi sering hilang." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak pernah menyimpan bukti transaksi." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T4.  Apakah Anda pernah mengalami kesulitan ketika membayar atau melaporkan pajak?",
                    options = listOf(
                        "Tidak pernah." to HealthStatus.BASIC,
                        "Kesulitan memahami cara menghitung pajak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kesulitan menggunakan sistem atau aplikasi perpajakan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengetahui batas waktu pembayaran dan pelaporan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memahami jenis pajak yang harus dibayarkan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "T5.  Bagaimana perusahaan mengevaluasi efektivitas kebijakan perpajakan terhadap strategi bisnis?",
                    options = listOf(
                        "Melalui periodic tax strategy review dan tax impact assessment." to HealthStatus.BASIC,
                        "Melalui evaluasi tax efficiency dan compliance." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Melalui konsultasi dengan penasihat pajak eksternal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Evaluasi hanya dilakukan ketika muncul risiko pajak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum melakukan evaluasi strategis perpajakan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F1.  Apakah uang yang tersedia saat ini cukup untuk membayar kebutuhan usaha sehari-hari?",
                    options = listOf(
                        "Selalu cukup." to HealthStatus.BASIC,
                        "Biasanya cukup tetapi terkadang mengalami kekurangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Sering kekurangan uang untuk kebutuhan operasional." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Harus menggunakan uang pribadi untuk menutup kebutuhan usaha." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Sering meminjam uang untuk menjalankan usaha." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F2.  Bagaimana Anda mengatur uang hasil penjualan?",
                    options = listOf(
                        "Langsung dibagi untuk modal, biaya, dan keuntungan." to HealthStatus.BASIC,
                        "Sebagian digunakan untuk kebutuhan usaha dan sebagian untuk pribadi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Digunakan sesuai kebutuhan saat itu." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memiliki pengaturan khusus." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F3.  Apakah pelanggan Anda sering membeli secara utang atau belum langsung membayar?",
                    options = listOf(
                        "Tidak menerima pembayaran secara utang." to HealthStatus.BASIC,
                        "Ada beberapa pelanggan yang membayar belakangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Sebagian besar penjualan menggunakan sistem pembayaran tertunda." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Banyak pelanggan terlambat membayar." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.COSTING,
                    questionText = "F4.  Bagaimana perusahaan mengintegrasikan financial planning dengan strategi korporasi?",
                    options = listOf(
                        "Terintegrasi dengan strategic planning, capital allocation, dan enterprise performance management." to HealthStatus.BASIC,
                        "Terintegrasi dengan annual business planning dan forecasting." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Digunakan sebagai dasar penyusunan anggaran." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya digunakan untuk pelaporan keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum terintegrasi dengan strategi korporasi." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.COSTING,
                    questionText = "C1.  Bagaimana Anda menentukan harga jual produk atau jasa?",
                    options = listOf(
                        "Menghitung seluruh biaya dan menambahkan keuntungan." to HealthStatus.BASIC,
                        "Menghitung biaya bahan baku saja." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengikuti harga pesaing." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengikuti harga pasar." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menentukan harga berdasarkan perkiraan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.COSTING,
                    questionText = "C2.  Apakah Anda mengetahui biaya yang dibutuhkan untuk menghasilkan satu produk atau memberikan satu layanan?",
                    options = listOf(
                        "Mengetahui seluruh biaya." to HealthStatus.BASIC,
                        "Mengetahui biaya bahan baku saja." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengetahui biaya bahan baku dan tenaga kerja." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya memperkirakan total biaya." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengetahui biaya per produk atau layanan." to HealthStatus.HIGH_ATTENTION
                    )
                )
            ),
            "Menengah (UKM)" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A5.  Apa kendala utama dalam proses pembukuan perusahaan?",
                    options = listOf(
                        "Dokumen transaksi tidak lengkap." to HealthStatus.BASIC,
                        "Keterlambatan pencatatan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kesalahan pencatatan transaksi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kesulitan menyusun laporan keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kurangnya tenaga yang memahami akuntansi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Sistem akuntansi belum memadai." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1.  Apakah perusahaan telah menerapkan kebijakan akuntansi dan prosedur pencatatan transaksi secara formal?",
                    options = listOf(
                        "Ya, seluruhnya terdokumentasi dan diterapkan secara konsisten." to HealthStatus.BASIC,
                        "Sudah memiliki kebijakan, tetapi belum mencakup seluruh transaksi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Prosedur tersedia tetapi belum terdokumentasi secara formal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pencatatan masih bergantung pada kebiasaan staf." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki kebijakan akuntansi formal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A2.  Berapa lama waktu yang dibutuhkan perusahaan untuk menyelesaikan proses monthly financial closing?",
                    options = listOf(
                        "Maksimal 5 hari kerja." to HealthStatus.BASIC,
                        "6–10 hari kerja." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "11–15 hari kerja." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Lebih dari 15 hari kerja." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki jadwal closing yang terstandarisasi." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A3.  Bagaimana mekanisme rekonsiliasi akun-akun material dalam laporan keuangan?",
                    options = listOf(
                        "Dilakukan setiap bulan dengan independent review." to HealthStatus.BASIC,
                        "Dilakukan setiap bulan oleh staf terkait." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dilakukan secara berkala tetapi tidak mencakup seluruh akun material." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dilakukan ketika ditemukan perbedaan saldo." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki prosedur rekonsiliasi formal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A4.  Bagaimana perusahaan memastikan keandalan informasi dalam laporan keuangan?",
                    options = listOf(
                        "Menggunakan review berjenjang dan dokumentasi pendukung." to HealthStatus.BASIC,
                        "Menggunakan review oleh manajer keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengandalkan pemeriksaan staf akuntansi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya melakukan pemeriksaan menjelang audit." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme pengendalian kualitas laporan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T5.  Apa kendala terbesar dalam mengelola perpajakan perusahaan?",
                    options = listOf(
                        "Kurangnya pemahaman peraturan." to HealthStatus.BASIC,
                        "Kesulitan menghitung pajak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dokumen perpajakan tidak lengkap." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kesulitan melakukan rekonsiliasi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memiliki staf khusus perpajakan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Perubahan regulasi yang sulit diikuti." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1.  Apakah perusahaan telah memiliki Tax Compliance Management System yang terdokumentasi?",
                    options = listOf(
                        "Ya, terdapat SOP, tax calendar, dan penanggung jawab yang jelas." to HealthStatus.BASIC,
                        "Memiliki SOP tetapi belum mencakup seluruh kewajiban pajak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pengelolaan pajak dilakukan oleh departemen keuangan tanpa SOP formal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengandalkan konsultan eksternal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki sistem kepatuhan pajak yang terstruktur." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T2.  Bagaimana perusahaan melakukan rekonsiliasi laporan keuangan komersial dengan laporan fiskal?",
                    options = listOf(
                        "Dilakukan secara berkala dengan dokumentasi lengkap." to HealthStatus.BASIC,
                        "Dilakukan setiap akhir tahun sebelum penyusunan SPT." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya dilakukan pada akun-akun tertentu." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dilakukan ketika terdapat perbedaan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki prosedur rekonsiliasi fiskal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T3.  Bagaimana perusahaan mengidentifikasi potensi risiko perpajakan?",
                    options = listOf(
                        "Melalui periodic tax risk assessment." to HealthStatus.BASIC,
                        "Melalui tax review sebelum pelaporan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengandalkan review konsultan eksternal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya melakukan evaluasi ketika terdapat pemeriksaan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme identifikasi risiko pajak." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T4.  Bagaimana perusahaan mengelola dokumentasi transaksi yang memiliki konsekuensi perpajakan?",
                    options = listOf(
                        "Seluruh dokumen disimpan secara sistematis dan mudah ditelusuri." to HealthStatus.BASIC,
                        "Sebagian besar dokumen tersimpan dengan baik." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dokumentasi masih tersebar di beberapa bagian." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dokumen sering tidak lengkap." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki sistem dokumentasi perpajakan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F4.  Bagaimana perusahaan memenuhi kebutuhan tambahan modal kerja?",
                    options = listOf(
                        "Menggunakan laba ditahan." to HealthStatus.BASIC,
                        "Menggunakan tambahan modal pemilik." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan pinjaman bank." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan pinjaman dari pihak lain." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki strategi pembiayaan yang jelas." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F1.  Apakah perusahaan telah menerapkan sistem Cash Flow Forecasting?",
                    options = listOf(
                        "Ya, menggunakan rolling cash flow forecast." to HealthStatus.BASIC,
                        "Menggunakan proyeksi arus kas bulanan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya membuat proyeksi tahunan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Proyeksi dilakukan ketika dibutuhkan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki sistem cash flow forecasting." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F2.  Bagaimana perusahaan mengevaluasi efektivitas pengelolaan working capital?",
                    options = listOf(
                        "Menggunakan analisis DSO, DIO, dan DPO." to HealthStatus.BASIC,
                        "Menggunakan analisis umur piutang dan persediaan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan monitoring saldo kas dan utang." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya mengevaluasi ketika terjadi kekurangan kas." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme evaluasi working capital." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F3.  Bagaimana perusahaan mengevaluasi kemampuan memenuhi kewajiban jangka pendek?",
                    options = listOf(
                        "Melalui liquidity ratio analysis secara berkala." to HealthStatus.BASIC,
                        "Melalui monitoring saldo kas dan kewajiban." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Berdasarkan perkiraan manajemen." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya ketika terjadi kesulitan pembayaran." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki evaluasi likuiditas formal." to HealthStatus.HIGH_ATTENTION
                    )
                )
            ),
            "Enterprise (Besar)" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A5.  Sejauh mana laporan keuangan digunakan dalam pengambilan keputusan manajemen?",
                    options = listOf(
                        "Sebagai dasar budgeting, forecasting, dan evaluasi kinerja." to HealthStatus.BASIC,
                        "Sebagai dasar evaluasi profitabilitas dan likuiditas." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya untuk kebutuhan perpajakan dan pelaporan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya digunakan ketika terjadi masalah keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum digunakan dalam pengambilan keputusan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1.  Bagaimana tingkat integrasi Financial Reporting System dengan sistem operasional perusahaan?",
                    options = listOf(
                        "Terintegrasi sepenuhnya melalui ERP dengan automated financial consolidation." to HealthStatus.BASIC,
                        "Terintegrasi pada sebagian besar fungsi bisnis." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Terintegrasi secara parsial dengan beberapa proses manual." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan sistem terpisah antarunit bisnis." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki integrasi sistem yang memadai." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A2.  Bagaimana perusahaan memastikan kepatuhan terhadap standar akuntansi yang berlaku dalam penyusunan laporan keuangan?",
                    options = listOf(
                        "Memiliki formal accounting policies, technical accounting review, dan periodic compliance assessment." to HealthStatus.BASIC,
                        "Memiliki kebijakan akuntansi dengan review oleh manajemen keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengandalkan review auditor eksternal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Evaluasi dilakukan ketika terdapat perubahan standar." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme evaluasi formal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A3.  Bagaimana mekanisme perusahaan dalam mengendalikan kualitas financial reporting?",
                    options = listOf(
                        "Melalui multi-level review, internal audit, dan automated controls." to HealthStatus.BASIC,
                        "Melalui management review dan periodic control assessment." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Melalui pemeriksaan oleh departemen keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pemeriksaan hanya dilakukan menjelang publikasi laporan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme yang terstruktur." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T5.  Apakah perusahaan pernah mengalami pemeriksaan, koreksi, atau sengketa perpajakan dalam tiga tahun terakhir?",
                    options = listOf(
                        "Tidak pernah." to HealthStatus.BASIC,
                        "Pernah mengalami klarifikasi dan telah diselesaikan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pernah mengalami pemeriksaan tanpa koreksi material." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pernah mengalami koreksi atau sanksi material." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Sedang menghadapi sengketa perpajakan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1.  Bagaimana tingkat kematangan Tax Governance Framework perusahaan?",
                    options = listOf(
                        "Enterprise-wide tax governance terintegrasi dengan corporate governance." to HealthStatus.BASIC,
                        "Formal tax compliance framework dengan pengawasan manajemen." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kebijakan perpajakan dikelola oleh departemen pajak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pengelolaan pajak terfragmentasi antarunit bisnis." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki tax governance framework yang komprehensif." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T2.  Bagaimana perusahaan mengimplementasikan Tax Risk Management?",
                    options = listOf(
                        "Menggunakan risk-based tax control framework dengan monitoring berkelanjutan." to HealthStatus.BASIC,
                        "Melakukan periodic tax risk assessment seluruh unit bisnis." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Melakukan evaluasi risiko berdasarkan transaksi material." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Evaluasi hanya dilakukan ketika terdapat perubahan regulasi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki tax risk management framework." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T3.  Bagaimana perusahaan melakukan monitoring terhadap perubahan regulasi perpajakan?",
                    options = listOf(
                        "Memiliki regulatory monitoring dan impact assessment terstruktur." to HealthStatus.BASIC,
                        "Dilakukan oleh departemen pajak secara berkala." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengandalkan konsultan pajak eksternal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Evaluasi dilakukan setelah regulasi mulai berdampak." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme regulatory monitoring." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T4.  Bagaimana perusahaan memastikan akurasi tax reporting pada seluruh unit bisnis?",
                    options = listOf(
                        "Menggunakan integrated tax reporting system dan automated reconciliation." to HealthStatus.BASIC,
                        "Menggunakan standardized reporting procedures dengan independent review." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan rekonsiliasi manual yang direview manajemen." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengandalkan laporan dari masing-masing unit bisnis." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki standardized tax reporting system." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F4.  Bagaimana perusahaan menentukan sumber pendanaan untuk ekspansi bisnis?",
                    options = listOf(
                        "Menggunakan analisis cost of capital dan financial feasibility." to HealthStatus.BASIC,
                        "Membandingkan biaya pinjaman dan kemampuan pembayaran." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Berdasarkan ketersediaan dana." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Berdasarkan keputusan pemilik." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki metode evaluasi pendanaan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F1.  Bagaimana perusahaan mengelola enterprise-wide liquidity risk?",
                    options = listOf(
                        "Menggunakan integrated treasury management dan liquidity forecasting." to HealthStatus.BASIC,
                        "Menggunakan centralized cash management." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Monitoring dilakukan oleh masing-masing unit bisnis." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Monitoring hanya dilakukan berdasarkan laporan periodik." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki liquidity risk management framework." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F2.  Bagaimana perusahaan mengoptimalkan working capital?",
                    options = listOf(
                        "Menggunakan integrated working capital optimization strategy." to HealthStatus.BASIC,
                        "Menggunakan KPI untuk receivables, inventory, dan payables." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Melakukan evaluasi pada masing-masing unit bisnis." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Melakukan perbaikan ketika terjadi tekanan likuiditas." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki strategi working capital formal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F3.  Bagaimana perusahaan mengevaluasi keputusan pendanaan dan struktur modal?",
                    options = listOf(
                        "Menggunakan capital structure optimization dan cost of capital analysis." to HealthStatus.BASIC,
                        "Menggunakan debt-equity ratio dan financial feasibility assessment." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan evaluasi biaya pendanaan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Berdasarkan kebutuhan pendanaan jangka pendek." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki mekanisme evaluasi formal." to HealthStatus.HIGH_ATTENTION
                    )
                )
            ),
            "Kecil" to listOf(
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A5.  Apakah Anda memiliki catatan transaksi yang dapat diperiksa kembali?",
                    options = listOf(
                        "Ya, seluruh transaksi memiliki catatan." to HealthStatus.BASIC,
                        "Sebagian besar transaksi tercatat." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya transaksi tertentu yang dicatat." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memiliki catatan transaksi." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A1.  Bagaimana sistem pencatatan transaksi keuangan yang diterapkan dalam usaha Anda?",
                    options = listOf(
                        "Pencatatan manual menggunakan buku." to HealthStatus.BASIC,
                        "Menggunakan spreadsheet." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan software akuntansi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan sistem terintegrasi dengan penjualan dan persediaan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki sistem pencatatan yang terstruktur." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A2.  Seberapa rutin perusahaan menyusun laporan keuangan?",
                    options = listOf(
                        "Setiap bulan." to HealthStatus.BASIC,
                        "Setiap tiga bulan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Setiap enam bulan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Setiap tahun." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya ketika dibutuhkan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum pernah menyusun laporan keuangan secara formal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A3.  Apakah perusahaan memiliki pencatatan khusus untuk aset, utang, piutang, dan persediaan?",
                    options = listOf(
                        "Seluruhnya dicatat dan diperbarui secara berkala." to HealthStatus.BASIC,
                        "Sebagian besar sudah dicatat." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya beberapa akun yang memiliki pencatatan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Belum memiliki pencatatan khusus." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.ACCOUNTING,
                    questionText = "A4.  Bagaimana proses pengecekan kesesuaian saldo kas dan rekening bank?",
                    options = listOf(
                        "Dilakukan setiap bulan dan didokumentasikan." to HealthStatus.BASIC,
                        "Dilakukan setiap bulan tetapi belum terdokumentasi." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dilakukan jika ditemukan selisih." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dilakukan hanya pada akhir tahun." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak pernah dilakukan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T5.  Apakah Anda mengetahui aturan pajak yang berlaku untuk jenis usaha Anda?",
                    options = listOf(
                        "Ya, memahami dan mengikuti perubahan aturan." to HealthStatus.BASIC,
                        "Mengetahui aturan dasar tetapi tidak memahami detailnya." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya mengetahui informasi dari orang lain." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak mengetahui aturan yang berlaku." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T1.  Bagaimana perusahaan mengelola administrasi dan kewajiban perpajakan?",
                    options = listOf(
                        "Dikelola oleh pemilik usaha." to HealthStatus.BASIC,
                        "Dikelola oleh staf administrasi atau keuangan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Dikelola oleh staf khusus perpajakan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan konsultan pajak eksternal." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Kombinasi staf internal dan konsultan eksternal." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T2.  Seberapa rutin perusahaan melakukan rekonsiliasi omzet dengan pelaporan pajak?",
                    options = listOf(
                        "Setiap bulan sebelum pelaporan." to HealthStatus.BASIC,
                        "Setiap tiga bulan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Setiap akhir tahun." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya ketika akan menyampaikan SPT Tahunan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak pernah melakukan rekonsiliasi." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T3.  Bagaimana perusahaan memastikan ketepatan waktu pembayaran dan pelaporan pajak?",
                    options = listOf(
                        "Memiliki tax calendar dan penanggung jawab khusus." to HealthStatus.BASIC,
                        "Menggunakan pengingat manual." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Mengandalkan pengingat dari konsultan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Melakukan pembayaran ketika mendekati batas waktu." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memiliki sistem pengingat." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.TAX,
                    questionText = "T4.  Apakah perusahaan pernah menerima surat klarifikasi atau teguran dari otoritas pajak?",
                    options = listOf(
                        "Tidak pernah." to HealthStatus.BASIC,
                        "Pernah menerima surat klarifikasi dan telah diselesaikan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pernah menerima SP2DK." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Pernah mengalami keterlambatan atau denda." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Sedang menghadapi permasalahan perpajakan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F4.  Apakah Anda memiliki dana cadangan untuk menghadapi kondisi usaha yang tidak terduga?",
                    options = listOf(
                        "Memiliki dana cadangan khusus usaha." to HealthStatus.BASIC,
                        "Memiliki dana cadangan tetapi jumlahnya terbatas." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Menggunakan tabungan pribadi jika diperlukan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memiliki dana cadangan." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F1.  Bagaimana perusahaan melakukan monitoring terhadap arus kas?",
                    options = listOf(
                        "Membuat laporan arus kas secara rutin." to HealthStatus.BASIC,
                        "Memantau pemasukan dan pengeluaran melalui spreadsheet." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya memeriksa saldo kas dan bank." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak melakukan monitoring secara terstruktur." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F2.  Apakah perusahaan memiliki perencanaan kebutuhan kas untuk beberapa bulan ke depan?",
                    options = listOf(
                        "Ya, menggunakan cash flow forecasting." to HealthStatus.BASIC,
                        "Memiliki perkiraan kebutuhan kas sederhana." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Hanya membuat rencana ketika dibutuhkan." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memiliki perencanaan arus kas." to HealthStatus.HIGH_ATTENTION
                    )
                ),
                DiagnosticQuestionItem(
                    pillar = PillarType.FINANCE,
                    questionText = "F3.  Bagaimana perusahaan mengelola piutang pelanggan?",
                    options = listOf(
                        "Memiliki kebijakan kredit dan monitoring umur piutang." to HealthStatus.BASIC,
                        "Memiliki daftar piutang dan melakukan penagihan rutin." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Penagihan dilakukan ketika jatuh tempo." to HealthStatus.IMPROVEMENT_REQUIRED,
                        "Tidak memiliki sistem monitoring piutang." to HealthStatus.HIGH_ATTENTION
                    )
                )
            ),
        )
    }
}
