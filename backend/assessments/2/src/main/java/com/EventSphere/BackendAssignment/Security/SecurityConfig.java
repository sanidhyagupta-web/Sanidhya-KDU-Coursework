package com.EventSphere.BackendAssignment.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/events").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/events").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/user/*/reserveTicket/*").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/user/*").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE,"/user/soft/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/user/*").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // simplest for Postman

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails admin = User.builder()
                .username("Sanidhya")
                .password(passwordEncoder.encode("abc"))
                .roles("USER")
                .build();

        UserDetails basic = User.builder()
                .username("Aakash")
                .password(passwordEncoder.encode("abc"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, basic);
    }
}
