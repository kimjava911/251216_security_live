package kr.java.security.controller;

import kr.java.security.model.entity.Memo;
import kr.java.security.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {
    private final MemoService memoService;

    @GetMapping
    // import org.springframework.ui.Model;
    public String list(Principal principal, Model model) {
        model.addAttribute("memos",
                memoService.getMyMemos(principal.getName()));
        return "memo/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Principal principal, Model model) {
        Memo memo = memoService.getMemo(id);

        // 본인 글인지 여부 -> 수정, 삭제
        boolean isOwner = memo.getAuthor().getUsername().equals(principal.getName());

        model.addAttribute("memo", memo);
        model.addAttribute("isOwner", isOwner);
        return "memo/detail";
    }
}
