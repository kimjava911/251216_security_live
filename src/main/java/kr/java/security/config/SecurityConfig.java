package kr.java.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security를 활성화
public class SecurityConfig {

    // SecurityFilterChain -> Spring Security에서 사용될 보안 규칙 설정
    // 람다(lambda) DSL 문법 -> 설정 작성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // URL별 접근 권한 설정
            .authorizeHttpRequests(
                    auth -> auth
                    // 먼저 작성된 문법이 우선권 가짐
                    .requestMatchers("/", "/auth/**").permitAll()
                    // requestMatchers - 뒤에 나열할 패턴과 일치하는 것들에 대하여
                    // permitAll - 로그인 세션 없어도 접속 허락
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    // ADMIN Role을 가지고 있는 대상에만 허용
                    .anyRequest().authenticated()
                    // anyRequest - 모든 http 요청
                    // authenticated - 로그인 후 세션이 만들어져 있어야한다
            )
            // 폼 로그인
            .formLogin(
                    form ->
//                            form
//                                    .permitAll()
                    form
                            .loginPage("/auth/login") // controller (custom)
                            .loginProcessingUrl("/auth/login") // POST <- Spring Security가 알아서 처리해줌
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)  // 성공 시 이동할 페이지
                            .failureUrl("/auth/login?error=true")
                            .permitAll()
            )
            // 로그아웃
            .logout(logout ->
                    logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/") // 성공 시 이동할 페이지
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 세션 쿠키 삭제
                        .permitAll())
        ;
        return http.build();
    }
}
