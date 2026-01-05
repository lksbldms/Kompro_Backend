package de.bazi.kompro_backend.service

import de.bazi.kompro_backend.dto.RegistrationRequest
import de.bazi.kompro_backend.entity.Account
import de.bazi.kompro_backend.entity.User
import de.bazi.kompro_backend.repository.UserRepository
import de.bazi.kompro_backend.repository.AccountRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    fun registerUser(request: RegistrationRequest): User {
        if (userRepository.findByEmail(request.email) != null) {
            throw RuntimeException("Email bereits vergeben")
        }

        val newAccount = accountRepository.save(Account())
        val newUser = User(
            account = newAccount,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password)!!,
            firstname = request.firstname,
            lastname = request.lastname,
            isActive = true
        )
        return userRepository.save(newUser)
    }
}
