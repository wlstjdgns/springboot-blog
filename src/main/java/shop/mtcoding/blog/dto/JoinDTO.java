package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 회원가입 API
 * 1. 주소(URL) : http://localhost:8080/join 컴퓨터주소:프로그램주소/엔드포인트
 * 2. method : POST (GET은 바디가 없어 마트에 샴푸요청하려 가는데 샴푸를 들고가진 않잖아)
 * 3. 요청 body : username=벨류(String)&password=벨류(String)&email=벨류(String)
 * 4. 마임타입 : x - www - form - urlencoded
 * 5. 응답 : view를 응답함.
 */
@Getter
@Setter
public class JoinDTO {
    // 회원가입을 위한 데이터 트렌스포오브젝트
    // 조인폼에 타입들을 보고 가는거야

    private String username;
    private String password;
    private String email;

}
