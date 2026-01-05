package de.bazi.kompro_backend.controller

import de.bazi.kompro_backend.dto.RegistrationRequest
import de.bazi.kompro_backend.service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegistrationRequest) {
        authService.registerUser(request)
    }
}
