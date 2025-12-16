package kr.java.security.controller;

import kr.java.security.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Spring Security -> Login Page (default -> custom page)
// -> POST 로그인 요청
// -> GET 로그아웃 요청 => Security의 설정 파일을 바탕으로 구현해줌
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error, // 로그인 실패 시 error <- true
            @RequestParam(required = false) String logout, // 로그아웃 성공 시 logout <- true
            // org.springframework.ui.Model;
            Model model
    ) {
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "로그아웃되었습니다");
        }
        return "auth/login"; // forward
    }
}
