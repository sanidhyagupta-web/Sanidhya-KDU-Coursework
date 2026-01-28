package com.LibraryManagement2.ProductionReadyLib.Security;

import com.LibraryManagement2.ProductionReadyLib.Entities.Role;
import com.LibraryManagement2.ProductionReadyLib.Entities.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/books/v1")
                                .hasRole("LIBRARIAN")

                                .requestMatchers(HttpMethod.PATCH, "/books/v1/*/catalog/*")
                                .hasRole("LIBRARIAN")

                                .requestMatchers(HttpMethod.GET, "/books/v1")
                                .hasAnyRole("LIBRARIAN", "MEMBER")

                                .requestMatchers(HttpMethod.POST, "/loans/v1/*/borrow/*")
                                .hasRole("MEMBER")

                                .requestMatchers(HttpMethod.POST, "/loans/v1/*/return/*")
                                .hasRole("MEMBER")

                                .requestMatchers(HttpMethod.GET, "/loans/v1/analytics/audit")
                                .hasRole("MEMBER")

                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()

                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
