package server.cps.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic((httpbasic)->httpbasic.disable())
                .csrf((csrf)->csrf.disable())

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement((sseionMangagement)->sseionMangagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorize)-> authorize
                        .requestMatchers("/api/**").permitAll() // 로그인 api
//                        .requestMatchers("/api/signup").permitAll() // 회원가입 api
//                        .requestMatchers("/api/member").permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);// JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

        return http.build();
    }


}