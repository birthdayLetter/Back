package com.springboot.letterbackend.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html/**", // 일부 환경에서 필요
                                "/swagger-resources/**",
                                "/webjars/**").permitAll());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/sign-api/sign-in",
                "/sign-api/sign-up",
                "/sign-api/kakao/sign-up",
                "/sign-api/kakao/**","/sign-api/exception","/sign-api/check/email","/friend/**","/user/profile/**","/friend/**","/api/letter/**").permitAll());
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**", "/app/**", "/friend/**", "/letter/**","/ws-stomp/**","/sub/**","/pub/**").permitAll());


        http.authorizeHttpRequests(authorizeRequests ->authorizeRequests.requestMatchers("/product/**").permitAll());
        http.authorizeHttpRequests(authorizeRequest-> authorizeRequest.requestMatchers("**exception**").permitAll());
        http.authorizeHttpRequests(authorizeRequest-> authorizeRequest.anyRequest().authenticated());
        http.sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(httpBasicBuilder -> httpBasicBuilder.disable());
        http.rememberMe(rememberMe->rememberMe.key("security").rememberMeParameter("rememberMe").tokenValiditySeconds(60*60*24*30));
        http.exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler()));
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(new CustomAuthenticatonEntryPoint()));
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        //사이트 위변조 요청 방지
      http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
      return http.build(); // ✅ 반드시 마지막에 build() 호출





  }
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(){
        return (web )->web.ignoring().requestMatchers("/v3/api-docs/**","swagger-resources/**",
                "/swagger-ui/**","/swagger-ui/index.html/**","/webjars/**","/sign-api/exception","/ws/**", "/app/**", "/friend/**", "/letter/**","/ws-stomp/**","/sub/**","/pub/**");
  }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ⭐ 정확한 출처만 명시해야 Access-Control-Allow-Origin이 올바르게 설정됩니다!
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 또는 배포 도메인

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
