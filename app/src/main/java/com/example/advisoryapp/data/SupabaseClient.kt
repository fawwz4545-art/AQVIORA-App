package com.example.advisoryapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object SupabaseClient {

    const val SUPABASE_URL = "https://hltdjkrlvkikxtiwtnpi.supabase.co"
    const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhsdGRqa3Jsdmtpa3h0aXd0bnBpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTI5MTc0MTAsImV4cCI6MjEwODQ5MzQxMH0.bIHEpakjDuCQ1hJPjS6FydrxEMAD2fVzU6ilOsyN90Q"

    suspend fun get(table: String, queryParams: String = "select=*"): JSONArray = withContext(Dispatchers.IO) {
        val endpoint = "$SUPABASE_URL/rest/v1/$table?$queryParams"
        val url = URL(endpoint)
        val conn = url.openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "GET"
            conn.setRequestProperty("apikey", SUPABASE_KEY)
            conn.setRequestProperty("Authorization", "Bearer $SUPABASE_KEY")
            conn.setRequestProperty("Accept", "application/json")
            conn.connectTimeout = 10000
            conn.readTimeout = 10000

            if (conn.responseCode in 200..299) {
                val reader = BufferedReader(InputStreamReader(conn.inputStream))
                val response = reader.readText()
                reader.close()
                JSONArray(response)
            } else {
                JSONArray()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            JSONArray()
        } finally {
            conn.disconnect()
        }
    }

    suspend fun post(table: String, jsonObject: JSONObject): Boolean = withContext(Dispatchers.IO) {
        val endpoint = "$SUPABASE_URL/rest/v1/$table"
        val url = URL(endpoint)
        val conn = url.openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "POST"
            conn.setRequestProperty("apikey", SUPABASE_KEY)
            conn.setRequestProperty("Authorization", "Bearer $SUPABASE_KEY")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Prefer", "return=representation,resolution=merge-duplicates")
            conn.doOutput = true
            conn.connectTimeout = 10000
            conn.readTimeout = 10000

            val writer = OutputStreamWriter(conn.outputStream)
            writer.write(jsonObject.toString())
            writer.flush()
            writer.close()

            conn.responseCode in 200..299
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            conn.disconnect()
        }
    }

    suspend fun patch(table: String, queryParams: String, jsonObject: JSONObject): Boolean = withContext(Dispatchers.IO) {
        val endpoint = "$SUPABASE_URL/rest/v1/$table?$queryParams"
        val url = URL(endpoint)
        val conn = url.openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "POST" // Overriding method for patch via X-HTTP-Method-Override or PostgREST patch
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH")
            conn.setRequestProperty("apikey", SUPABASE_KEY)
            conn.setRequestProperty("Authorization", "Bearer $SUPABASE_KEY")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true
            conn.connectTimeout = 10000
            conn.readTimeout = 10000

            val writer = OutputStreamWriter(conn.outputStream)
            writer.write(jsonObject.toString())
            writer.flush()
            writer.close()

            conn.responseCode in 200..299
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            conn.disconnect()
        }
    }
}
