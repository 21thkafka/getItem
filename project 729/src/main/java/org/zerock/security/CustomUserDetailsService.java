package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.domain.CustomUser;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// 스프링 시큐리티에서는
// 인증을 수행하는 객체 : UserDetailsService 인터페이스를 구현
@Log4j
public class CustomUserDetailsService implements UserDetailsService {
// MemberMapper를 주입받는다.: 실제 데이터베이스 테이블을 가져오기 위해 
	@Setter(onMethod_=@Autowired)
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("Load User By UserName : " + username);	// 동작하는지 확인을 위해 로깅
		
		MemberVO vo = memberMapper.read(username);		// 실질적인 고객 정보 + 권한정보
		log.warn("queried by member mapper: " + vo);
		return vo == null ? null : new CustomUser(vo);	// CustomUser가 UserDtail 인터페이스를 구현한 객체
		// UserDetains를 구현한 객체를 반환해주어야 한다.
	}

}
