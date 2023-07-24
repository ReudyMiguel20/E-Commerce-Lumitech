package com.lumitech.ecommerceapp.common;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        // This is needed for H2 console to work properly with Spring Security enabled
        MvcRequestMatcher h2RequestMatcher = new MvcRequestMatcher(introspector, "/**");
        h2RequestMatcher.setServletPath("/h2-console");

        http

                .csrf(csrf -> {
                    csrf.disable();
                    csrf.ignoringRequestMatchers("/h2-console/**");
                })


                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(h2RequestMatcher).permitAll()
                            .requestMatchers("/api/user/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("ADMIN", "USER") //It works for user
                            .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyAuthority("ADMIN", "USER") //It works for user
                            .anyRequest()
                            .permitAll();
                })


                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors().disable();
        http.headers().frameOptions().disable();


        return http.build();
    }
}
