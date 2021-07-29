package org.zerock.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.AuthVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberAuthMapper;
import org.zerock.mapper.MemberMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
	@Setter(onMethod_ = {@Autowired})
	private MemberMapper mapper;
	
	@Setter(onMethod_ = {@Autowired})
	private MemberAuthMapper authMapper;
	
	@Setter(onMethod_ = {@Autowired})
	private PasswordEncoder pwencoder;
	
	
	@Transactional
	@Override
	public void join(MemberVO member) {
		log.info("MemberServie:join()");
		
		String encPw=pwencoder.encode(member.getPassword());
		member.setPassword(encPw);
		
		mapper.join(member);
		AuthVO auth = new AuthVO();
		auth.setUserid(member.getUserid());
		auth.setAuth("ROLE_USER");
		authMapper.join(auth);
	}
	@Override
	public String idCheck(String userid) {
		return mapper.idCheck(userid);
	}
	@Override
	public MemberVO userInfo(String userid) {
		return mapper.userInfo(userid);
	}
	@Override
	public List<BoardVO> saleInfo(String userid) {
		return mapper.saleInfo(userid);
	}
	
}
