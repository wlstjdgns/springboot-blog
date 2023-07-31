package shop.mtcoding.blog.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

    // //디스패쳐서블릿이 바디데이터를 파싱안하고 컨트롤러 메서드만 찾은 상황
    // //클래스는 각자 책임을 두고 그일을 했는지 안했는지 분석
    // //디스패쳐 서블릿이 (컨트롤러 메서드찾기,바디데이터 파싱)
    // //파싱을 내가 메서드에서 직접 한거다.
    // @PostMapping("/join") // /엔드포인트
    // public String join(HttpServletRequest request) {// request로 바로받겟다 파싱안하고 밑에서
    // 파싱한것
    // String username = request.getParameter("username");
    // // 스트링타입->톰캣이 변환->서블릿-자바로 http 파싱해주는 소켓클래스 -리퀘스트객체로 만들어줘
    // // ->컨트롤러의 메서드/디스패쳐서블릿(컨트롤러에 주소를보고 찾아주는 애)폼 유알엘 인코디드(키=벨류&키=벨류)로 들어온애를
    // // 파싱해준다. 그리고 메서드를 찾기(우리가 그때 리플렉션으로 한 것)//컨트롤러에 배분하는거까지 스프링이 해주는 일
    // // DAO만들고 그런건 그냥 자바고 이제 컨트롤러만 우리가 신경쓰면 다 해줘//분석하는건 다 파싱이야.
    // String password = request.getParameter("password");
    // String email = request.getParameter("email");
    // return "redirect:/loginForm";
    // }

    // @PostMapping("/join") // /엔드포인트
    // public String join(HttpServletRequest request) { // 리퀘스트 안에있는걸 자기가 직접 2차 파싱을
    // 한거야
    // BufferedReader br = request.getReader();
    // //username=ssar&password=1234&email=ssar@nate.com
    // 버퍼라는 곳에 접근하겠다는 뜻

    // String body = br.readLine();
    // //버퍼가 소비되었음
    // String username = request.getParameter("username");
    // //이거 실행 안돼 버퍼를 이미 소비했는데 뭘 또 파싱하라는거야.
    // return null; //이거 답이없는거야
    // }

    // @PostMapping("/join")
    // public String join(String username, String password, String email) {
    // System.out.println("username: " + username);
    // System.out.println("username: " + password);
    // System.out.println("username: " + email);

    // return "redirect:/loginForm";
    // }

    @Autowired // 레파지토리에 의존시켜줘야해
    private UserRepository userRepository;

    // 실무
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        } // validation check(유효성검사)
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirect:/40x";
        }
        userRepository.save(joinDTO); // 디비에 접근해서 비지니스 로직처리하는 모든걸 모델이라고 한다. 추상적인 의미인것

        return "redirect:/loginForm";
    }

    // ip주소 부여: 10.5.9.200:8080 -> mtcoding.com:8080을 돈주고 사 내아이피랑 연결하는거야
    // localhost,127.0.0.1
    // a태그 form태그 method=get
    @GetMapping("/joinForm")
    public String joinForm() {

        // templates/
        // .mustache
        // templates//user/joinForm.mustache
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}
// 카드만들어서 설정하기 위아래 설명넣고