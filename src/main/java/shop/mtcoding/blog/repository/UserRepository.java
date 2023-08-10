package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;

//$ BoardController,UserController,UserRepository
//* EntityManager,HttpSesstion 스프링이 띄워주는거
//* 디펜더씨인젝션하면 땡겨올수이써 @오토와이어드
@Repository // * IoC에 올리는거야. 알아서 new해주네
public class UserRepository {

    @Autowired
    private EntityManager em; // * 예전의 DB커넥션

    public User findByUsername(String username) {
        try {
            Query query = em.createNativeQuery("select * from user_tb where username=:username",
                    User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);// 애는 디비랑 똑같이 생겼으니까 바로 받을수있고 안그러면 DTO만들고
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());

        return (User) query.getSingleResult();
    }

    @Transactional
    public void save(JoinDTO joinDTO) {
        System.out.println("테스트 :" + 1);
        Query query = em
                .createNativeQuery(
                        "insert into user_tb(username, password, email) values(:username, :password, :email)");
        System.out.println("테스트 :" + 2);
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        System.out.println("테스트 :" + 3);
        query.executeUpdate(); // 쿼리를 전송 (DBMS)
        System.out.println("테스트 :" + 4);
    }
}
