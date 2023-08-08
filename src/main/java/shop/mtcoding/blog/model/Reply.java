package shop.mtcoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
// $ User(1) - Reply(N)
// $ Board(1) - Reply(N)
@Entity
@Table(name = "reply_tb")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String comment; // 댓글 내용

    @JoinColumn(name = "user_id") // * 디폴트가 이 값이다. 다른값으로 바꾸고 싶을때 쓴다.
    @ManyToOne
    private User user; // * FK */ user_id 포링키
    @ManyToOne
    private Board board; // * FK */ board_tb 포링키

}
