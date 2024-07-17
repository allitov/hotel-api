package com.allitov.hotelapi.configuration;

import com.allitov.hotelapi.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * The configuration class for spring security.
 * @author allitov
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);

        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           AuthenticationManager authenticationManager,
                                           AuthenticationEntryPoint authenticationEntryPoint,
                                           AccessDeniedHandler accessDeniedHandler) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**")
                            .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user")
                            .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user/**")
                            .hasAnyAuthority(User.RoleType.USER.name(), User.RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**")
                            .hasAnyAuthority(User.RoleType.USER.name(), User.RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/hotel/**", "/api/v1/room/**")
                            .hasAnyAuthority(User.RoleType.USER.name(), User.RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/hotel/**")
                            .hasAnyAuthority(User.RoleType.USER.name(), User.RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/booking")
                            .hasAnyAuthority(User.RoleType.USER.name(), User.RoleType.ADMIN.name())
                        .anyRequest()
                            .hasAuthority(User.RoleType.ADMIN.name()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager)
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        return httpSecurity.build();
    }
}
