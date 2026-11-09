package com.example.advisoryapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.advisoryapp.model.BookingRequest
import com.example.advisoryapp.model.Expert
import com.example.advisoryapp.model.FreeQuestion
import com.example.advisoryapp.model.PillarType
import com.example.advisoryapp.model.UserProfile

class AppDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "aqviora_internal.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 1. Users Table
        db.execSQL(
            """
            CREATE TABLE users (
                id TEXT PRIMARY KEY,
                full_name TEXT NOT NULL,
                company_name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                phone TEXT,
                industry TEXT,
                business_scale TEXT
            )
            """.trimIndent()
        )

        // 2. Free Questions Table
        db.execSQL(
            """
            CREATE TABLE free_questions (
                id TEXT PRIMARY KEY,
                user_id TEXT,
                question_text TEXT NOT NULL,
                category TEXT,
                ai_basic_answer TEXT,
                recommended_pillar TEXT,
                cta_text TEXT,
                timestamp TEXT
            )
            """.trimIndent()
        )

        // 3. Experts Table
        db.execSQL(
            """
            CREATE TABLE experts (
                id TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                title TEXT NOT NULL,
                primary_pillar TEXT NOT NULL,
                secondary_pillars TEXT,
                years_experience INTEGER,
                rating REAL,
                review_count INTEGER,
                bio TEXT,
                certifications TEXT,
                hourly_rate REAL,
                is_available INTEGER
            )
            """.trimIndent()
        )

        // 4. Booking Requests Table
        db.execSQL(
            """
            CREATE TABLE booking_requests (
                id TEXT PRIMARY KEY,
                user_id TEXT,
                expert_id TEXT,
                expert_name TEXT,
                booking_date TEXT,
                time_slot TEXT,
                client_topic TEXT,
                duration_minutes INTEGER,
                status TEXT
            )
            """.trimIndent()
        )

        // 5. Health Results Table
        db.execSQL(
            """
            CREATE TABLE health_results (
                id TEXT PRIMARY KEY,
                user_id TEXT,
                pillar TEXT,
                status TEXT,
                score INTEGER,
                finding_summary TEXT,
                recommended_next_step TEXT
            )
            """.trimIndent()
        )

        // Seed Initial Experts Data into Local Database
        seedInitialExperts(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS free_questions")
        db.execSQL("DROP TABLE IF EXISTS experts")
        db.execSQL("DROP TABLE IF EXISTS booking_requests")
        db.execSQL("DROP TABLE IF EXISTS health_results")
        onCreate(db)
    }

    private fun seedInitialExperts(db: SQLiteDatabase) {
        val experts = listOf(
            arrayOf("exp-1", "Budi Santoso, SE, Ak, CA, BKP", "Senior Tax & Accounting Advisory Specialist", "TAX", "ACCOUNTING,FINANCE", "15", "4.9", "128", "Mantan Auditor Big 4 & Konsultan Pajak Terdaftar (BKP C). Berpengalaman mendampingi 200+ perusahaan manufaktur, retail & jasa.", "BKP C (Konsultan Pajak), CA (Chartered Accountant), Certified Tax Advisor", "500000.0", "1"),
            arrayOf("exp-2", "Siti Rahmawati, M.Ak, CMA", "Costing & Budgeting Advisory Specialist", "COSTING", "BUDGETING,FINANCE", "12", "4.85", "94", "Praktisi Manajemen Biaya & Anggaran Sektor Manufaktur, Kuliner (F&B) dan Distribusi. Ahli penyusunan HPP & Variance Analysis.", "CMA (Certified Management Accountant), Certified Budget Professional", "450000.0", "1"),
            arrayOf("exp-3", "Hendrik Wijaya, SE, CIA, CRMA", "Internal Control & Internal Audit Advisor", "INTERNAL_CONTROL", "INTERNAL_AUDIT,RISK_MANAGEMENT", "16", "4.95", "110", "Spesialis Pengendalian Internal, Pencegahan Fraud, SOP & Internal Audit. Pengalaman memimpin tim audit di holding group ternama.", "CIA (Certified Internal Auditor), CRMA (Risk Management Assurance)", "550000.0", "1"),
            arrayOf("exp-4", "Diana Putri, M.Sc, CFA", "Financial Analysis & Corporate Advisory", "FINANCE", "BUDGETING,RISK_MANAGEMENT", "10", "4.8", "76", "Ahli Financial Advisory, Cash Flow Optimization, & Restrukturisasi Modal Kerja untuk UMKM dan Bisnis Skala Menengah.", "CFA Charterholder, Financial Modeling Specialist", "400000.0", "1")
        )

        for (exp in experts) {
            val cv = ContentValues().apply {
                put("id", exp[0])
                put("name", exp[1])
                put("title", exp[2])
                put("primary_pillar", exp[3])
                put("secondary_pillars", exp[4])
                put("years_experience", exp[5].toInt())
                put("rating", exp[6].toDouble())
                put("review_count", exp[7].toInt())
                put("bio", exp[8])
                put("certifications", exp[9])
                put("hourly_rate", exp[10].toDouble())
                put("is_available", exp[11].toInt())
            }
            db.insert("experts", null, cv)
        }
    }

    // CRUD Operations for Internal Local Database (Create, Read, Update, Delete)

    fun insertUser(user: UserProfile): Boolean {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("id", user.id)
            put("full_name", user.fullName)
            put("company_name", user.companyName)
            put("email", user.email)
            put("phone", user.phone)
            put("industry", user.industry)
            put("business_scale", user.businessScale)
        }
        val result = db.insertWithOnConflict("users", null, cv, SQLiteDatabase.CONFLICT_REPLACE)
        return result != -1L
    }

    fun getUserByEmail(email: String): UserProfile? {
        val db = readableDatabase
        val cursor = db.query(
            "users",
            null,
            "LOWER(email) = ?",
            arrayOf(email.lowercase().trim()),
            null,
            null,
            null
        )

        var user: UserProfile? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"))
            val companyName = cursor.getString(cursor.getColumnIndexOrThrow("company_name"))
            val userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
            val industry = cursor.getString(cursor.getColumnIndexOrThrow("industry"))
            val scale = cursor.getString(cursor.getColumnIndexOrThrow("business_scale"))
            user = UserProfile(id, fullName, companyName, userEmail, phone, industry, scale)
        }
        cursor.close()
        return user
    }

    fun getAllExperts(): List<Expert> {
        val db = readableDatabase
        val cursor = db.query("experts", null, null, null, null, null, null)
        val list = mutableListOf<Expert>()

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val pPillarStr = cursor.getString(cursor.getColumnIndexOrThrow("primary_pillar"))
            val primaryPillar = try { PillarType.valueOf(pPillarStr) } catch (_: Exception) { PillarType.ACCOUNTING }

            val secStr = cursor.getString(cursor.getColumnIndexOrThrow("secondary_pillars")) ?: ""
            val secPillars = secStr.split(",").mapNotNull {
                try { PillarType.valueOf(it.trim()) } catch (_: Exception) { null }
            }

            val years = cursor.getInt(cursor.getColumnIndexOrThrow("years_experience"))
            val rating = cursor.getDouble(cursor.getColumnIndexOrThrow("rating"))
            val reviews = cursor.getInt(cursor.getColumnIndexOrThrow("review_count"))
            val bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"))
            val certsStr = cursor.getString(cursor.getColumnIndexOrThrow("certifications")) ?: ""
            val certs = certsStr.split(",").map { it.trim() }
            val rate = cursor.getDouble(cursor.getColumnIndexOrThrow("hourly_rate"))
            val available = cursor.getInt(cursor.getColumnIndexOrThrow("is_available")) == 1

            list.add(Expert(id, name, title, primaryPillar, secPillars, years, rating, reviews, bio, certs, rate, available))
        }
        cursor.close()
        return list
    }

    fun insertFreeQuestion(question: FreeQuestion): Boolean {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("id", question.id)
            put("user_id", "usr-1")
            put("question_text", question.questionText)
            put("category", question.category?.name ?: "ACCOUNTING")
            put("ai_basic_answer", question.aiBasicAnswer)
            put("recommended_pillar", question.recommendedPillar?.name ?: "ACCOUNTING")
            put("cta_text", question.ctaText)
            put("timestamp", question.timestamp)
        }
        val result = db.insertWithOnConflict("free_questions", null, cv, SQLiteDatabase.CONFLICT_REPLACE)
        return result != -1L
    }

    fun getAllFreeQuestions(): List<FreeQuestion> {
        val db = readableDatabase
        val cursor = db.query("free_questions", null, null, null, null, null, "id DESC")
        val list = mutableListOf<FreeQuestion>()

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
            val questionText = cursor.getString(cursor.getColumnIndexOrThrow("question_text"))
            val catStr = cursor.getString(cursor.getColumnIndexOrThrow("category"))
            val category = try { PillarType.valueOf(catStr) } catch (_: Exception) { PillarType.ACCOUNTING }
            val answer = cursor.getString(cursor.getColumnIndexOrThrow("ai_basic_answer"))
            val cta = cursor.getString(cursor.getColumnIndexOrThrow("cta_text"))
            val ts = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))

            list.add(FreeQuestion(id, ts, questionText, category, answer, category, cta))
        }
        cursor.close()
        return list
    }

    fun deleteFreeQuestion(questionId: String): Int {
        val db = writableDatabase
        return db.delete("free_questions", "id = ?", arrayOf(questionId))
    }

    fun insertBooking(booking: BookingRequest): Boolean {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("id", booking.id)
            put("user_id", "usr-1")
            put("expert_id", booking.expertId)
            put("expert_name", booking.expertName)
            put("booking_date", booking.date)
            put("time_slot", booking.timeSlot)
            put("client_topic", booking.clientTopic)
            put("duration_minutes", booking.durationMinutes)
            put("status", booking.status)
        }
        val result = db.insertWithOnConflict("booking_requests", null, cv, SQLiteDatabase.CONFLICT_REPLACE)
        return result != -1L
    }

    fun getAllBookings(): List<BookingRequest> {
        val db = readableDatabase
        val cursor = db.query("booking_requests", null, null, null, null, null, "id DESC")
        val list = mutableListOf<BookingRequest>()

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
            val expId = cursor.getString(cursor.getColumnIndexOrThrow("expert_id"))
            val expName = cursor.getString(cursor.getColumnIndexOrThrow("expert_name"))
            val date = cursor.getString(cursor.getColumnIndexOrThrow("booking_date"))
            val slot = cursor.getString(cursor.getColumnIndexOrThrow("time_slot"))
            val topic = cursor.getString(cursor.getColumnIndexOrThrow("client_topic"))
            val duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration_minutes"))
            val status = cursor.getString(cursor.getColumnIndexOrThrow("status"))

            list.add(BookingRequest(id, expId, expName, date, slot, topic, duration, status))
        }
        cursor.close()
        return list
    }

    fun deleteBooking(bookingId: String): Int {
        val db = writableDatabase
        return db.delete("booking_requests", "id = ?", arrayOf(bookingId))
    }
}
