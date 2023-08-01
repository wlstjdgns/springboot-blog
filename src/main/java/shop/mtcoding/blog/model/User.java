package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_tb")
@Entity // * ddl - auto 가 create
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // * 오토인클리먼트 적용됩니다.
    @Column(unique = true)
    private Integer id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false, length = 20)
    private String email;

}
