package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 $ 글수정 API
 ? 1. 주소(URL) : http://localhost:8080/board/{id}/update 컴퓨터주소:프로그램주소/엔드포인트
 ? 2. method : POST
 ? 3. 요청 body : title=벨류(String)&content=벨류(String)
 ? 4. 마임타입 : x - www - form - urlencoded
 ? 5. 응답 : view를 응답함 . detail 페이지
 */
@Getter
@Setter
public class UpdateDTO {
    private String title;
    private String content;
}
