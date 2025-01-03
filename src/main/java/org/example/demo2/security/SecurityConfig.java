package org.example.demo2.security;

import org.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permite accesul la resurse statice pentru toți utilizatorii
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Restricții pentru utilizatori autentificați
                        .requestMatchers("/dashboard/**").hasAnyAuthority("PARTICIPANT", "ORGANIZER")
                        .requestMatchers("/competitions/add").hasAuthority("ORGANIZER") // Doar ORGANIZER poate accesa
                        .requestMatchers("/competitions").hasAnyAuthority("ORGANIZER","PARTICIPANT") // Acces la lista competițiilor
                        .requestMatchers("/competitions/*").hasAnyAuthority("ORGANIZER","PARTICIPANT") // Acces la detaliile competiției
                        .requestMatchers("/competitions/**").hasAuthority("ORGANIZER") // Sub-rute doar pentru ORGANIZER
                        .requestMatchers("/participants/add").hasAuthority("ORGANIZER") // Numai ORGANIZER poate accesa
                        // Alte resurse publice
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/dashboard", true) // Redirecționează mereu la /dashboard după logare
                        .permitAll() // Permite accesul la pagina implicită de login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

}
