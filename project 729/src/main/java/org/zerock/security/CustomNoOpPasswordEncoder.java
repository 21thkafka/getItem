package org.zerock.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

// 스프링 시큐리티에서는 PasswordEncorder를 사용하도록 요구 : 암호화
// 암호화를 하지 않고 인증을 하는 인코더를 정의
@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		log.info("before encode : " + rawPassword);
		return rawPassword.toString();	// 암호화를 하지 않으므로 toString으로 문장열로 변환
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		log.warn("matches: " + rawPassword + ":" + encodedPassword);
		return rawPassword.toString().equals(encodedPassword);
	}

}
