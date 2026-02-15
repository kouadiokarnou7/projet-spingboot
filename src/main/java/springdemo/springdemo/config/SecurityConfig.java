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
        
        // Utilisateur normal
        UserDetails jean = User.builder()
                .username("jean")
                .password(encoder.encode("jean21"))
                .roles("USER")
                .build();

        // Administrateur de l'application
        UserDetails marie = User.builder()
                .username("marie")
                .password(encoder.encode("password"))
                .roles("ADMIN")
                .build();

        

        return new InMemoryUserDetailsManager(jean, marie);
    }

    // 2. Configuration de la sécurité HTTP
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
                // Ressources publiques
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                
                // ✅ H2 Console - UNIQUEMENT pour DBA
                .requestMatchers("/h2-console/**").hasRole("DBA")
                
                // API Admin - pour ADMIN
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                // API Employés - pour USER et ADMIN
                .requestMatchers("/api/employes/**").hasAnyRole("USER", "ADMIN")
                
                // Tout le reste
                .anyRequest().authenticated()
            )
            
            // Login avec redirection selon le rôle
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((req, res, auth) -> {
                    // Redirection selon le rôle
                    for (var r : auth.getAuthorities()) {
                        String role = r.getAuthority();
                        
                        if (role.equals("ROLE_DBA")) {
                            res.sendRedirect("/h2-console");
                            return;
                        }
                        if (role.equals("ROLE_ADMIN")) {
                            res.sendRedirect("/api/admin/dashboard");
                            return;
                        }
                    }
                    // Par défaut pour USER
                    res.sendRedirect("/liste-employes");
                })
                .permitAll()
            )
            
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}