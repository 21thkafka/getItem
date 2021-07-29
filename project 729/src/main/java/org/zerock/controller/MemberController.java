package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.MemberVO;
import org.zerock.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/*")
public class MemberController {
	@Autowired
	private MemberService service;
	
	@GetMapping("/join")
	public void join() {
		
	}
	
	@PostMapping(value="/join")
	public String join(MemberVO member, RedirectAttributes rttr) {
		log.info("join 진입");
		log.info("register: " + member);
		service.join(member);
		rttr.addFlashAttribute("result", member.getUserid());
		
		return "redirect:/customLogin";
	}
	
	
	// 아이디 중복 검사
	@PostMapping(value = "/idCheck")	
	public @ResponseBody String idCheck(String userid){
		log.info("idCheck() 진입");
		return service.idCheck(userid) != null ? "using" : "not using";
		
	}
	
	// 유저 페이지
	@GetMapping(value = "/users/userInfo")
	public void userInfo(String userid, Model model) {
		log.info("유저 페이지");
		model.addAttribute("user", service.userInfo(userid));

	}
	
	// 판매 내역
	@GetMapping(value = "/users/saleInfo")
	public void saleInfo(String userid, Model model) {
		log.info("판매 내역 페이지");
		model.addAttribute("list", service.saleInfo(userid));
	}
	// 판매 중 or 판매 완료
	@PostMapping("/users/soldout")
	public void soldout(){
		log.info("soldout");
	}
}
