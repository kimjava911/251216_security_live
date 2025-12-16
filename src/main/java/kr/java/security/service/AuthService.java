package kr.java.security.service;

import kr.java.security.model.entity.UserAccount;
import kr.java.security.model.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAccountRepository userAccountRepository; // save, exists...
    private final PasswordEncoder passwordEncoder; // Security Config -> Bean

    public void signup(String username, String rawPassword) {
        // 1. 중복 가입 (SQL Unique 검증되긴 하는데 가능하면 서버단에서 처리하는게 에러 내는 것보다 나음)
        if (userAccountRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디 입니다");
        }
        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println(encodedPassword);
        // 3. 사용자 생성 및 저장
        UserAccount user = new UserAccount(
            username,
            encodedPassword, // 암호화된 비밀번호를 DB 저장
            "ROLE_USER"
        );
        // 4. repo로 save
        userAccountRepository.save(user);
    }
}
