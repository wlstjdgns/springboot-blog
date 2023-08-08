package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyRepository replyRepository; // 1번 만들기 보드레파지토리 참고 (V)

    @PostMapping("/reply/save")
    public String save(ReplyWriteDTO replyWriteDTO) {
        // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // * comment 유효성 검사 (안들고 올 수 도 있으니까)
        // 보드 아이디가 널인지 공백인지 (V)
        if (replyWriteDTO.getBoardId() == null) {// BoardId가 null이거나 비어있으면40x로 간다.

            return "redirect:/40x";
        } // 코멘트가 널인지 공백인지 (V)
        if (replyWriteDTO.getComment() == null || replyWriteDTO.getComment().isEmpty()) {// Comment가null이거나비어있으면40x로간다.
            return "redirect:/40x";
        }
        // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // * 인증검사(인증없으면 못써야지)
        // 세션 아이디가 널인지 아이디가 인증안되면 로그인페이지로 돌리기
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401
        }
        // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // *댓글 쓰기
        replyRepository.save(replyWriteDTO, sessionUser.getId());
        System.out.println("댓글쓰기완료");
        // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // *상세보기 페이지로 리턴
        return "redirect:/board/" + replyWriteDTO.getBoardId();
    }

    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id) { // ! 1. PathVariable 값 받기

        // ! 2.인증검사
        // session에 접근해서 sessionUser 키값을 가져오세요
        // null 이면, 로그인페이지로 보내고
        // null 아니면, 3번을 실행하세요.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401
        }
        // ! 3. 권한검사
        // Reply reply = replyRepository.findByBoardId(id);
        // if (reply.getUser().getId() != sessionUser.getId()) {
        // return "redirect:/40x"; // 403 권한없음
        // }

        // ! 4. 모델에 접근해서 삭제
        // boardRepository.deleteById(id); 호출하세요 -> 리턴을 받지 마세요
        // delete from board_tb where id = :id
        replyRepository.deleteById(id);

        return "redirect:/";
    }
}
