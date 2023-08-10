package shop.mtcoding.blog;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
	public static void main(String[] args) {
		String encPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
		System.out.println("encPassword: " + encPassword);
		boolean isValid = BCrypt.checkpw("15234", encPassword);
		System.out.println(isValid); // 솔트가 랜덤하게 만들어진다.

	}

}
