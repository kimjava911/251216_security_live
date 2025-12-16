package kr.java.security.service;

import kr.java.security.model.entity.Memo;
import kr.java.security.model.entity.UserAccount;
import kr.java.security.model.repository.MemoRepository;
import kr.java.security.model.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
// import org.springframework.transaction.annotation.Transactional;
@Transactional(readOnly = false)
public class MemoService {
    private final MemoRepository memoRepository;
    private final UserAccountRepository userAccountRepository;

    // 현재 로그인한 사용자의 메모 목록 조회
    public List<Memo> getMyMemos(String username) {
        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return memoRepository.findByAuthorIdWithAuthor(user.getId());
    }

    // 메모 상세 조회
    public Memo getMemo(Long id) {
        return memoRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));
    }

    // 메모 작성
    @Transactional
    public Memo createMemo(String title, String content, String username) {
        UserAccount author = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Memo memo = new Memo();
        memo.setTitle(title);
        memo.setContent(content);
        memo.setAuthor(author);
        return memoRepository.save(memo);
    }

    // 메모 수정
    @Transactional
    @PreAuthorize("#username == authentication.name")
//    public boolean updateMemo(Long id, String title, String content, String username) {
    public void updateMemo(Long id, String title, String content, String username) {
        // -> AccessDeniedException
        Memo memo = getMemo(id);

//        if (!memo.getAuthor().getUsername().equals(username)) {
//            return false; // 본인 글이 아니라면 false 처리
//        }

        memo.setTitle(title);
        memo.setContent(content); // Dirty Checking
//        return true;
    }

    // 메모 삭제
    @Transactional
    public boolean deleteMemo(Long id, String username) {
        Memo memo = getMemo(id);

        if (!memo.getAuthor().getUsername().equals(username)) {
            return false; // 본인 글이 아니라면 false 처리
        }

        memoRepository.delete(memo);
        return true;
    }
}
