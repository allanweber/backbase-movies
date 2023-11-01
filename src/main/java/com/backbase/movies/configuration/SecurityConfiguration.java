package com.backbase.movies.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final ServerAuthenticationExceptionEntryPoint serverAuthenticationExceptionEntryPoint;

    public SecurityConfiguration(ServerAuthenticationExceptionEntryPoint serverAuthenticationExceptionEntryPoint) {
        this.serverAuthenticationExceptionEntryPoint = serverAuthenticationExceptionEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//

        httpSecurity
                .exceptionHandling(custom -> custom.authenticationEntryPoint(serverAuthenticationExceptionEntryPoint))
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                                .requestMatchers(getPublicPath()).permitAll()
                                        .anyRequest().authenticated()
                )
                .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .cors(cors -> cors.configurationSource(getCorsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return httpSecurity.build();
    }
}
