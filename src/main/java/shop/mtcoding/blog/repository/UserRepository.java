package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        System.out.println("테스트1");
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);// 애는 디비랑 똑같이 생겼으니까 바로 받을수있고 안그러면 DTO만들고
        System.out.println("테스트2");
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        System.out.println("테스트3");

        return (User) query.getSingleResult();
    }

    @Transactional // * 트랜잭션이 적용된 프록시 객체 생성 /프록시 객체는 해당 어노테이션이 포함된 메서드가 호출될 경우
    // * 트랜잭션을 시작하고 커밋 or 롤백을 수행한다. CheckedExcoption or 기준 커밋/롤백
    public void save(JoinDTO joinDTO) {// $ 컨트롤러가 디비인서트하려고 받은걸 얘가 전달 받아야하는거야.
        Query query = em
                .createNativeQuery("insert into user_tb(username,password,email) values(:username,:password,:email) ");
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        query.executeUpdate(); // * mall참고
    }
}
