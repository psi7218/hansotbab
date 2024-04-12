package com.b209.hansotbab.user.config;

import com.b209.hansotbab.user.handler.CustomAccessDeniedHandler;
import com.b209.hansotbab.user.handler.CustomAuthenticationEntryPoint;
import com.b209.hansotbab.user.handler.CustomLogoutHandler;
import com.b209.hansotbab.user.handler.CustomLogoutSuccessHandler;
import com.b209.hansotbab.user.jwt.JWTAuthenticationFilter;

import com.fasterxml.jackson.core.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomLogoutHandler logoutHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final JWTAuthenticationFilter authenticationFilter;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler,
                          CustomLogoutHandler logoutHandler, CustomLogoutSuccessHandler logoutSuccessHandler, JWTAuthenticationFilter authenticationFilter) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.logoutHandler = logoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(sessionConfigurer -> sessionConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/user/**").permitAll()
                        .requestMatchers("/api/v1/fridge/recent").permitAll()
                        .requestMatchers("/api/v1/fridge/**").permitAll()
                        .requestMatchers("/api/v1/review/**").authenticated()
                        .requestMatchers("/api/v1/wishlist/fridge/**").permitAll()
                        .requestMatchers("/api/v1/wishlist/**").authenticated()
                        .anyRequest().permitAll())

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))

                .logout(httpLogout -> httpLogout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/user/logout"))
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .deleteCookies("JSESSIONID", "access_token", "refresh_token", "remember-me")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
