package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;

@Controller
public class boardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping({ "/", "/board" })
    public String index() {
        return "index";
    }

    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // $ 부가로직 (유효성검사)
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {// *username이 null이거나 비어있으면 /40x 로간다.
            return "redirect:/40x";
        } // ? validation check(유효성검사)
        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            return "redirect:/40x";
        }
        // ! 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirext:loginForm";
        }
        boardRepository.save(writeDTO, sessionUser.getId());
        return "redirect:/";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // * 인증이 필요한 페이지는 무조건 다시 돌아가도록 만듦 */
        }
        return "/board/saveForm";

    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id) {
        return "/board/detail";
    }

}
