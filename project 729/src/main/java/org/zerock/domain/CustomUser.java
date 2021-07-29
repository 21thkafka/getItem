package org.zerock.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

// 인증 결과물 : 사용자 정보 + 권한 정보
// User 객체를 상속 받아야 한다.
// User 객체가 UserDetails 인터페이스를 구현했으므로, CustomerUser 객체도 UserDatails 인터페이스를 구현
@Getter
public class CustomUser extends User {
	private static final long serialVersionUID = 1L;
	
	private MemberVO member;
	
	public CustomUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomUser(MemberVO vo) {
		super(vo.getUserid(), vo.getPassword(), vo.getAuthList().stream()
				.map(auth -> new
						SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
				this.member=vo;
	}

}
