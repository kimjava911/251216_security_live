package kr.java.security.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security를 활성화
@EnableMethodSecurity // AOP -> PreAuthorize
public class SecurityConfig {

    // SecurityFilterChain -> Spring Security에서 사용될 보안 규칙 설정
    // 람다(lambda) DSL 문법 -> 설정 작성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // URL별 접근 권한 설정
            .authorizeHttpRequests(
                    auth -> auth
                    // JSP Forwarding
                    // jakarta.servlet.DispatcherType
                    .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 1. BCrypt -> only. $2affffffffff...
        // 기본적으로는 BCrypt를 사용하지만 상황에 따라서 여러가지 인증.
//        return new BCryptPasswordEncoder();
        // 2. PasswordEncoderFactories.createDelegatingPasswordEncoder() -> BCrypt
        // https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html
        // https://docs.spring.io/spring-security/reference/6.5/features/authentication/password-storage.html
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // 호환성 높음
        // {bcrypt}$2a1111111...
        // {noop}12345
    }
}
