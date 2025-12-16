package kr.java.security.model.repository;

import kr.java.security.model.entity.Memo;
import kr.java.security.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    // spring security <- user <= username (login인할 때)
    // username -> (?) -> password <-> eq. -> session
    Optional<UserAccount> findByUsername(String username);

    // 중복 체크를 위한 (존재여부)
    boolean existsByUsername(String username);
}
