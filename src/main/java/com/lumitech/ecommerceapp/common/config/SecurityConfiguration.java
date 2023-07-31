package com.lumitech.ecommerceapp.common.config;

import com.lumitech.ecommerceapp.common.jwt.JwtAuthenticationFilter;
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
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/products/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
                            .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
                            .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
                            .anyRequest()
                            .authenticated();
                })

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors().disable();
        http.headers().frameOptions().disable();
        http.httpBasic()
//                .and().formLogin();

        return http.build();
    }
}
