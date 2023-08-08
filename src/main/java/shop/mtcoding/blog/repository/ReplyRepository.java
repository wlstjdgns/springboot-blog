package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;

// $ 컨트롤러,레파지토리와 스프링에서 띄워주는 엔티티매니저,HttpSession 다 떠잇는거야.
@Repository
public class ReplyRepository {
    @Autowired
    private EntityManager em;

    public List<Reply> findByBoardId(Integer boardId) {
        Query query = em.createNativeQuery("select * from reply_tb where board_id = :boardId", Reply.class);
        query.setParameter("boardId", boardId);
        return query.getResultList();
    }

    @Transactional // * 트랜잭션이 적용된 프록시 객체 생성 /프록시 객체는 해당 어노테이션이 포함된 메서드가 호출될 경우
    // * 트랜잭션을 시작하고 커밋 or 롤백을 수행한다. CheckedExcoption or 기준 커밋/롤백
    public void save(ReplyWriteDTO replyWriteDTO, Integer userId) {// $ 컨트롤러가 디비인서트하려고 받은걸 얘가 전달 받아야하는거야.
        System.out.println("댓글쓰기완료1");

        Query query = em
                .createNativeQuery("insert into reply_tb(comment,board_id,user_id) values(:comment,:boardId,:userId) ");
        System.out.println("댓글쓰기완료2");

        query.setParameter("comment", replyWriteDTO.getComment());
        query.setParameter("boardId", replyWriteDTO.getBoardId());
        query.setParameter("userId", userId);
        System.out.println("댓글쓰기완료3");

        query.executeUpdate(); // * mall참고 //* 쿼리를 전송 */ 쿼리를 DBMS에게 한다.
        System.out.println("댓글쓰기완료4");

    }

    @Transactional
    public void deleteById(Integer id) {
        Query query = em
                .createNativeQuery(
                        "delete from reply_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
