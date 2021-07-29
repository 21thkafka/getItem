package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// 회원 정보를 잘 가져오는지 테스트
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class MemberMapperTests {
		@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;
	
	@Test
	public void testRead() {
		MemberVO vo = mapper.read("admin90");
		log.info(vo);
		vo.getAuthList().forEach(authVO -> log.info(authVO));
	} 
	
/*	@Autowired
	private MemberMapper mapper;
	//아이디 중복검사
	@Test
	public void memberIdChk() throws Exception{
		String id = "admin90";
		String id2 = "test123";
		/*
		 * mapper.idCheck(id); mapper.idCheck(id2);
		 
	}*/
}
