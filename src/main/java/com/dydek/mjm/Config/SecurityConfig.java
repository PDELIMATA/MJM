package com.dydek.mjm.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sign-in", "/logout", "/").permitAll()
                        .requestMatchers("/map").authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/sign-in")
                                .loginProcessingUrl("/perform-login")
                                .defaultSuccessUrl("/map")
                                .failureUrl("/sign-in?error=true"))
                .logout(out ->
                        out
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/sign-in?logout=true")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID", "remember-me"))
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedPage("/403")
                )
                .rememberMe(Customizer.withDefaults());
        return http.build();
    }
}
