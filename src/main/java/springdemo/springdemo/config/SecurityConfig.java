package springdemo.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 1. Définition des utilisateurs en mémoire
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();
        
        UserDetails jean = User.builder()
                .username("jean")
                .password(encoder.encode("jean21"))
                .roles("USER")
                .build();

        UserDetails marie = User.builder()
                .username("marie")
                .password(encoder.encode("password"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(jean, marie);
    }

                    @Bean
            public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                    // CSRF : désactivé pour H2 et API
                    .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/api/**")
                    )

                    // Autoriser les frames pour H2
                    .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                    )

                    // Autorisations
                    .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/employes/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                    )

                    // Login
                    .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((req, res, auth) -> {
                            for (var r : auth.getAuthorities()) {
                                if (r.getAuthority().equals("ROLE_ADMIN")) {
                                    res.sendRedirect("/api/admin/dashboard");
                                    return;
                                }
                            }
                            res.sendRedirect("/liste-employes");
                        })
                        .permitAll()
                    )

                    .logout(logout -> logout.permitAll());

                return http.build();
            }

}