import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // Wichtig für Stateless APIs
            .authorizeHttpRequests { auth ->
                auth
                    // Erlaubt alle Swagger & OpenAPI Ressourcen explizit
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            // Wir schalten die Standard-Logins komplett aus
            .formLogin { it.disable() }
            .httpBasic { it.disable() }

        return http.build()
    }
}