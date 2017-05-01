package pl.edu.pw.elka.rso.eres3.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt encoder for testing purposes
 * @author psrok1
 */

public class PasswordEncoderGenerator {

	public static void main(String[] args) {
		int i = 0;
		while (i < 10) {
			String password = "123456";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);

			System.out.println(hashedPassword);
			i++;
		}

	}
}