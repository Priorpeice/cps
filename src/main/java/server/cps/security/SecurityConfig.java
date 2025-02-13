package server.cps.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic((httpbasic)->httpbasic.disable())
                .csrf((csrf)->csrf.disable())

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement((sseionMangagement)->sseionMangagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize)-> authorize
                        .requestMatchers("/" ).permitAll()
                        .requestMatchers("/api/test").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/fileDownload").hasRole("USER")
                        .requestMatchers(HttpMethod.GET , "/api/submission/detail/{submissionId}").permitAll()
                        .requestMatchers(HttpMethod.GET , "/api/board/{boardId}").permitAll()
                        .requestMatchers(HttpMethod.GET , "/api/problem/{problemId}").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/verification").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/problem").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/api/board").hasRole("USER") // 로그인 api
                        .requestMatchers(HttpMethod.POST,"/api/board/{boardId}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/api/submit").hasRole("USER")
                        .requestMatchers("/api/member/details").hasRole("USER")
                        .requestMatchers("/api/auth/member").permitAll() // 회원가입 api
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/rt").permitAll()
                        .requestMatchers("/api/member").hasRole("USER")
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
//                        .anyRequest().permitAll())
                .exceptionHandling(handler -> handler.authenticationEntryPoint(authenticationEntryPoint))
;// JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

        return http.build();
    }


}