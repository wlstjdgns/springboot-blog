package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 $ 로그인 API
 ? 1. 주소(URL) : http://localhost:8080/login 컴퓨터주소:프로그램주소/엔드포인트
 ? 2. method : POST (로그인은 select지만 post로 한다.)
 ? 3. 요청 body : username=벨류(String)&password=벨류(String)
 ? 4. 마임타입 : x - www - form - urlencoded
 ? 5. 응답 : view를 응답함.
 */
@Getter
@Setter
public class LoginDTO {
    private String username;
    private String password;
}
