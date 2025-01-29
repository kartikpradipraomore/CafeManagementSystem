package com.cafemanagementsys.config;

import com.cafemanagementsys.entity.Admin;
import com.cafemanagementsys.repository.AdminRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/signup", "/admin/add").permitAll() // Allow signup and admin addition
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Protect other /admin routes
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/admin/login") // Custom login page
                        .loginProcessingUrl("/admin/login/check") // Must match form action
                        .defaultSuccessUrl("/admin/index", true) // Redirect to dashboard on success
                        .failureUrl("/admin/login?error=true") // Redirect to login on failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout=true") // Redirect after logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(AdminRepository adminRepository) {
        return email -> {
            Admin admin = adminRepository.findByEmail(email);
            if (admin == null) {
                throw new UsernameNotFoundException("Admin not found");
            }
            return User.withUsername(admin.getEmail())
                    .password(admin.getPassword()) // Stored password (must match encoding)
                    .roles("ADMIN") // Assign role to user
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Secure encoding
    }
}
