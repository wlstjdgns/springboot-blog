package shop.mtcoding.blog.controller;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

    @Autowired // $ 레파지토리에 의존시켜줘야해
    private UserRepository userRepository;

    @Autowired // $ IoC컨테이너에 넣는다.
    private HttpSession session;// * requset는 가방, session은 서랍 */

    @GetMapping("/check")
    public ResponseEntity<String> check(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<String>("유저네임이 중복 되었습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("유저네임을 사용할 수 있습니다", HttpStatus.OK);
    }

    @PostMapping("/login") // * POST메서드로 엔드포인트에 요청이 오면 login메서드가 실행
    public String login(LoginDTO loginDTO) { // * 요청으로부터 전달된 LoginDTO객체를 파라미터로 받는다. / LoginDTO는 사용자 로그인 정보를 담은 전송 객체 */
        // ! 부가로직 (유효성검사)
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {// *username이 null이거나 비어있으면 /40x로 간다.
            return "redirect:/40x";
        } // * validation check(유효성검사)
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {// * password가 null이거나 비어있으면 /40x로 간다.
            return "redirect:/40x";
        }
        // ! 핵심 로직
        try {
            User user = userRepository.findByUsername(loginDTO.getUsername());
            boolean isValid = BCrypt.checkpw(loginDTO.getPassword(), user.getPassword());
            if (isValid) {
                session.setAttribute("sessionUser", user);
                return "redirect:/";
            } else {
                return "redirect:/loginForm";
            }
        } catch (Exception e) {
            return "redirect:/exLogin";
        }

    }

    // ! 실무
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        } // ? validation check(유효성검사)
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirect:/40x";
        }

        User user = userRepository.findByUsername(joinDTO.getUsername());
        if (user != null) {
            return "redirect:/50x";
        }

        String encPassword = BCrypt.hashpw(joinDTO.getPassword(), BCrypt.gensalt());
        joinDTO.setPassword(encPassword);
        System.out.println(encPassword.length());

        userRepository.save(joinDTO); // 핵심 기능
        return "redirect:/loginForm";
    }
    // try {
    // // ssar 넣으면 터지고
    // userRepository.findByUsername(joinDTO.getUsername());
    // return "redirect:/50x";

    // } catch (Exception e) {
    // // ssar1 넣으면 터지겠지
    // userRepository.save(joinDTO);
    // return "redirect:/loginForm";
    // }

    // try { //*아직 내가 상상하지 못하는 오류들만 트라이/캐치로 잡는거고 미리 잡을수있는 애들은 미리 잡아줘야해 */
    // userRepository.save(joinDTO); // * 디비에 접근해서 비지니스 로직처리하는 모든걸 모델이라고 한다. 추상적인
    // 의미인것

    // } catch (Exception e) {
    // return "redirect:/50x"; // 알고 있는데 터뜨릴필요는 없다. 미리 예방을 해야지....
    // }

    // * ip주소 부여: 10.5.9.200:8080 -> mtcoding.com:8080을 돈주고 사 내아이피랑 연결하는거야
    // * localhost,127.0.0.1
    // ! a태그 form태그 method=get
    @GetMapping("/joinForm")
    public String joinForm() {

        // * templates/
        // * .mustache
        // * templates//user/joinForm.mustache
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        // 세션값을 안쓰는 이유 1. 회원정보 - (많은정보)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401
        } // 유니크는 인덱스로 탄다.
        User user = userRepository.findByUsername(sessionUser.getUsername());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션 무효화 (서랍비우기)

        return "redirect:/";
    }

}
// * 카드만들어서 설정하기 위아래 설명넣고

// $디스패쳐서블릿이 바디데이터를 파싱안하고 컨트롤러 메서드만 찾은 상황
// $ 클래스는 각자 책임을 두고 그일을 했는지 안했는지 분석
// $ 디스패쳐 서블릿이 (컨트롤러 메서드찾기,바디데이터 파싱)
// $ 파싱을 내가 메서드에서 직접 한거다.
// $ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
// ? @PostMapping("/join") // /엔드포인트
// ? public String join(HttpServletRequest request) {// request로 바로받겟다 파싱안하고 밑에서
// * 파싱한것
// ? String username = request.getParameter("username");
// * 스트링타입->톰캣이 변환->서블릿-자바로 http 파싱해주는 소켓클래스 -리퀘스트객체로 만들어줘
// * ->컨트롤러의 메서드/디스패쳐서블릿(컨트롤러에 주소를보고 찾아주는 애)폼 유알엘 인코디드(키=벨류&키=벨류)로 들어온애를
// * 파싱해준다. 그리고 메서드를 찾기(우리가 그때 리플렉션으로 한 것)//컨트롤러에 배분하는거까지 스프링이 해주는 일
// * DAO만들고 그런건 그냥 자바고 이제 컨트롤러만 우리가 신경쓰면 다 해줘//분석하는건 다 파싱이야.
// ? String password = request.getParameter("password");
// ? String email = request.getParameter("email");
// ? return "redirect:/loginForm";
// ? }
// $ ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
// ? @PostMapping("/join") // /엔드포인트
// ? public String join(HttpServletRequest request) { // 리퀘스트 안에있는걸 자기가 직접 2차
// * 파싱을
// * 한거야
// ? BufferedReader br = request.getReader();
// ? username=ssar&password=1234&email=ssar@nate.com
// * 버퍼라는 곳에 접근하겠다는 뜻

// ? String body = br.readLine();
// * 버퍼가 소비되었음
// ? String username = request.getParameter("username");
// ! 이거 실행 안돼 버퍼를 이미 소비했는데 뭘 또 파싱하라는거야.
// ?return null; //! 이거 답이없는거야
// ? }
// $ ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
// ? @PostMapping("/join") //엔드포인트
// ? public String join(String username, String password, String email) {
// ? System.out.println("username: " + username);
// ? System.out.println("username: " + password);
// ? System.out.println("username: " + email);

// ? return "redirect:/loginForm";
// ? }

// // localhost:8080/check?username=ssar
// @GetMapping("/check") // ResponseEntity는 responsebody를 안붙여도 데이터를 응답하게 해줘
// 스트링타입의 바디를.
// // 첫번재로 안붙여도 되고 두번째로 리스폰스에다가 내가 직접 안넣어줘도 돼 뭘?선택권을
// public ResponseEntity<String> check(String username) {
// try {
// Query query = em.createNativeQuery)
// userRepository.findByUsername(username);
// return new ResponseEntity<>("중복됨", HttpStatus.BAD_REQUEST); // 응답될 내용과
// 상태코드이다. 눌러보면 400코드 //조금더자세히 보고싶으면
// // 바디데이터를 보라는거야
// } catch (Exception e) {
// return new ResponseEntity<>("중복되지 않음", HttpStatus.OK); // 눌러보면 200코드

// }
// }
// localhost:8080/check?username=ssar
// @GetMapping("/check")
// public ResponseEntity<String> check(String username) {
// User user = userRepository.findByUsername(username);
// if (user != null) {
// return new ResponseEntity<String>("유저네임이 중복 되었습니다", HttpStatus.BAD_REQUEST);
// }
// return new ResponseEntity<String>("유저네임을 사용할 수 있습니다", HttpStatus.OK);
// }
// localhost:8080/check?username=ssar