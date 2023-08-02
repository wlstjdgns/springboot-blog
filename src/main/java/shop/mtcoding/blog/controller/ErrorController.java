package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/40x")
    public String ex40x() {
        return "error/ex40x";
    }

    @GetMapping("/50x")
    public String ex50x() {
        return "error/ex50x";
    }

    // * 엔드포인트로 오는 get 요청을 처리하는 메서드 */
    @GetMapping("/exLogin")
    public String exLogin() {
        return "error/exLogin";
    }
}// *해당 뷰를 사용하여 로그인 오류와 관련된 정보를 보여줄 수 있도록 *
