package com.project.wsda.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/user/**").permitAll()
                    .requestMatchers("/shop/**").hasAuthority("shop")
                    .requestMatchers("/company/**").hasAuthority("company")
                    .requestMatchers("/cards/check-card").permitAll()
                    .requestMatchers("/cards/**").hasAnyAuthority("shop", "company")
                    .requestMatchers("/").permitAll()
                    .anyRequest()
                    .authenticated()
                )
                .exceptionHandling(h -> h
                        .accessDeniedPage("/access-denied")
                )
                .formLogin(form -> form
                    .loginPage("/user/login")
                    .successHandler(new LoginSuccessHandler())
                    .permitAll()
                )
                .logout(logout -> logout
                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                     .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
