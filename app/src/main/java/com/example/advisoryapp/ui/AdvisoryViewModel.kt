package com.example.advisoryapp.ui

import androidx.lifecycle.ViewModel
import com.example.advisoryapp.data.AdvisoryRepository
import com.example.advisoryapp.model.ActionItem
import com.example.advisoryapp.model.BookingRequest
import com.example.advisoryapp.model.ClientProject
import com.example.advisoryapp.model.DocumentItem
import com.example.advisoryapp.model.Expert
import com.example.advisoryapp.model.FreeQuestion
import com.example.advisoryapp.model.HealthResult
import com.example.advisoryapp.model.InvoiceItem
import com.example.advisoryapp.model.UserProfile
import kotlinx.coroutines.flow.StateFlow

class AdvisoryViewModel(
    private val repository: AdvisoryRepository = AdvisoryRepository()
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = repository.isLoggedIn
    val currentUser: StateFlow<UserProfile?> = repository.currentUser

    val freeQuestions: StateFlow<List<FreeQuestion>> = repository.freeQuestions
    val healthResults: StateFlow<List<HealthResult>> = repository.healthResults
    val experts: StateFlow<List<Expert>> = repository.experts
    val bookings: StateFlow<List<BookingRequest>> = repository.bookings
    val actionItems: StateFlow<List<ActionItem>> = repository.actionItems
    val projects: StateFlow<List<ClientProject>> = repository.projects
    val documents: StateFlow<List<DocumentItem>> = repository.documents
    val invoices: StateFlow<List<InvoiceItem>> = repository.invoices

    suspend fun login(email: String, pass: String): Boolean {
        return repository.loginUserOnline(email, pass)
    }

    fun register(
        fullName: String,
        companyName: String,
        email: String,
        phone: String,
        industry: String
    ): Boolean {
        return repository.registerUser(fullName, companyName, email, phone, industry)
    }

    fun logout() {
        repository.logout()
    }

    fun submitQuestion(text: String): FreeQuestion {
        return repository.askFreeQuestion(text)
    }

    fun bookConsultation(expertId: String, date: String, timeSlot: String, topic: String): BookingRequest {
        return repository.bookExpert(expertId, date, timeSlot, topic)
    }

    fun toggleActionItem(id: String) {
        repository.toggleActionItem(id)
    }

    fun approveProposal(projectId: String) {
        repository.approveProposal(projectId)
    }

    fun updateHealthCheckResults(results: List<HealthResult>) {
        repository.updateHealthScores(results)
    }
}
