package de.bazi.kompro_backend.dto

import jakarta.validation.constraints.*

data class RegistrationRequest(
    @field:Email(message = "Ungültige E-Mail Adresse")
    val email: String,

    @field:Size(min = 8, message = "Passwort muss mindestens 8 Zeichen lang sein")
    val password: String,

    val firstname: String?,
    val lastname: String?
)
