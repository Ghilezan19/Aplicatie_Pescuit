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
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/dashboard/**").hasAnyAuthority("PARTICIPANT", "ORGANIZER")
                        .requestMatchers("/competitions/add").hasAuthority("ORGANIZER") //
                        .requestMatchers("/competitions").hasAnyAuthority("ORGANIZER","PARTICIPANT")
                        .requestMatchers("/competitions/*").hasAnyAuthority("ORGANIZER","PARTICIPANT")
                        .requestMatchers("/competitions/**").hasAuthority("ORGANIZER")
                        .requestMatchers("/participants/add").hasAuthority("ORGANIZER")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
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
