// Golondrina, Airene M

package edu.uep.cos.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a Spring Security configuration class
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF protection (commonly done for APIs or simple school projects)
                .csrf(csrf -> csrf.disable())

                // Define which requests are allowed without authentication
                .authorizeHttpRequests(auth -> auth
                        // Publicly accessible endpoints and resources
                        .requestMatchers(
                                "/events/update",   // Allows event update requests
                                "/events/**",       // Allows all event-related endpoints
                                "/export/",         // Allows export functionality
                                "/LoginPage.html",  // Allows access to login page
                                "/login",           // Allows login processing
                                "/EventDash.html",  // Allows event dashboard page
                                "/style.css",       // Allows loading of CSS styles
                                "/pictures/**",     // Allows loading of images
                                "/js/**"            // Allows loading of JavaScript files
                        ).permitAll()

                        // Any other request is also permitted
                        // (No authentication required for this system)
                        .anyRequest().permitAll()
                );

        // Build and return the configured security filter chain
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt encoder used to securely hash passwords before storing them
        return new BCryptPasswordEncoder();
    }
}
