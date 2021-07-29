package org.zerock.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

// 교재에는 SampleController라고 되어 있지만
// SampleController가 이미 존재하므로
// SecurityController로 변경하였음.
@Log4j
@RequestMapping("/security/*")	// 기존 url "/sample"과 중복을 피하기 위하여 "/security라고 함 
@Controller
public class SecurityController {
	@GetMapping("/all")		// 모든 사용자가 접근 할 수 있는 url
	public void doAll() {
		log.info("do all can access everybody");
	}	// jsp 페이지 url : /security/all.jsp
	
	@GetMapping("/member")	// 로그인한 사용자만 접근 가능
	public void doMember() {
		log.info("logined member");
	}	// jsp 페이지 url : /security/all.jsp
	
	@GetMapping("/admin")	// 관리자만 접근 가능한 url
	public void doAdmin() {
		log.info("admin only");
	}	// jsp 페이지 url : /security/all.jsp
	
	// 스프링 시큐리티를 위한 어노테이션을 인식하기 위하여 servlet-context.xml에서 설정
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
	@GetMapping("/annoMember")
	public void doMember2() {
		log.info("logined annotation member");
	}	// jsp url : /security/annoMember.jsp
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/annoAdmin")
	public void doAdmin2() {
		log.info("admin annotation only");
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/authMember")
	public void doMembe3() {
		log.info("인증된 member");
	}	// jsp url : /security/annoMember.jsp
}
