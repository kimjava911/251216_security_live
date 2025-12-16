package kr.java.security.controller;

import kr.java.security.model.entity.Memo;
import kr.java.security.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/new")
    public String createForm() {
        return "memo/form";
    }

    @PostMapping("/new")
    public String create(
            @RequestParam String title,
            @RequestParam String content,
            Principal principal
    ) {
        memoService.createMemo(title, content, principal.getName());
        return "redirect:/memo";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Long id,
            Principal principal,
            Model model
    ) {
        Memo memo = memoService.getMemo(id);

        if (!memo.getAuthor().getUsername().equals(principal.getName())) {
            return "redirect:/memo"; // 본인 글이 아닌데 수정 시도
        }

        model.addAttribute("memo", memo);
        return "memo/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            Principal principal
    ) {
//        boolean success = memoService.updateMemo(id, title, content, principal.getName());
        memoService.updateMemo(id, title, content, principal.getName());
//        if (success) {
            return "redirect:/memo/" + id;
//        }
//        return "redirect:/memo"; // 권한 없이 post 시도를 했으면...
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        memoService.deleteMemo(id, principal.getName());
        return "redirect:/memo";
    }
}
