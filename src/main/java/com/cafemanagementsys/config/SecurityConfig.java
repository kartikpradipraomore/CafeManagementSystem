package com.cafemanagementsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing (enable in production with proper
                                              // configuration)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").authenticated() // Protect admin routes
                        .anyRequest().permitAll()) // Allow public access to other routes
                .formLogin(form -> form
                        .loginPage("/admin/login") // Custom login page
                        .defaultSuccessUrl("/admin/dashboard", true) // Redirect to dashboard after login
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/admin/logout") // Logout URL
                        .logoutSuccessUrl("/") // Redirect to home after logout
                        .permitAll());

        return http.build();
    }

}
