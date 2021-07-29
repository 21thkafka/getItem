package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.LikeVO;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")		// 어느 url에 따라 동작할지 지정
@Log4j
@AllArgsConstructor			// BoardService와 연동을 위해 주입받기 위해
public class BoardController {
	private BoardService service;	// 주입 받음
	
	//게시글 목록 보기
/*	@GetMapping("/list")
	public void List(Model model) {
		log.info("list...");
		model.addAttribute("list", service.getList());
	}*/
	
	// 게시글 쓰기를 위한 폼을 보여주기
	// 게시글 쓰기를 인증한 사용자만 허용
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {
		
	}
	
	// 인증이 된 사용자만이 게시글 쓰기를 할 수 있음
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("=================================");	// 로깅
		log.info("register: " + board);
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		log.info("===================================");
		service.register(board);
//		rttr.addAttribute("result",board.getBno());
		rttr.addFlashAttribute("result",board.getBno());	// 게시글 등록에 성공하면 파업창을 띄운다
			// 추가된 bno를 출력해 준다.(새로고침을 눌렀을 때 다시 팝업창이 뜨는 것을 막기 위해 )
		return "redirect:/board/list";
	}
	
	@GetMapping({"/get", "/modify"})	// {}의 의미 : 배열
	public void get(@RequestParam("bno") Integer bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or /modify");
		model.addAttribute("board", service.get(bno));		
	}	// jsp 페이지 : get-> /board/get.jsp, update-> /board/update.jsp를 보여준다
	
	
	@PostMapping("/modify")
	@PreAuthorize("principal.username == #board.userid")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify: " + board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("RESULT", "SUCCESS");
		}
/*		rttr.addAttribute("pageNum", cri.getPageNum()); //-> cri.getListLink()로 대체
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword()); */
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@PostMapping("/remove")
	@PreAuthorize("principal.username == #userid")
	public String remove(@RequestParam("bno") Integer bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String userid) {
		log.info("remove..."+bno);
		
		// 첨부파일 목록에 대한 정보를 가져온다.
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) {
			// 첨부 파일을 삭제(데이터베이스가 삭제가 성공하면 파일을 삭제)
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}
/*		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());*/
		return "redirect:/board/list" + cri.getListLink();
	}

	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() ==0) {
			return;
		}
		log.info("delete attach files.....................");
		log.info(attachList);
		
		attachList.forEach(attach ->{
			try {
				  Path file  = Paths.get("C:\\SMH\\upload\\"+attach.getUploadPath()+"\\" + attach.getUuid()+"_"+ attach.getFileName());
			      Files.deleteIfExists(file);		//원본 파일 삭제
			      if(Files.probeContentType(file).startsWith("image")) {
			          Path thumbNail = Paths.get("C:\\SMH\\upload\\"+attach.getUploadPath()+"\\s_" + attach.getUuid()+"_"+ attach.getFileName());
			          Files.delete(thumbNail);	// 섬네일 삭제
			        }
			      }catch(Exception e) {
			        log.error("delete file error" + e.getMessage());
			      }//end catch
		});
	}

	/*
	 * @GetMapping("/list") public void List(Criteria cri, Model model) {
	 * log.info("list..." + cri); model.addAttribute("list", service.getList(cri));
	 * // 게시글 목록 // 페이징 처리를 위해 PageDTO 객체를 전달 -> JSP 페이지에서 페이징처리를 한다.
	 * model.addAttribute("pageMaker", new PageDTO(cri, 123)); // total을 계산해 주어야 함 }
	 */
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list: " + cri);
		model.addAttribute("list", service.getList(cri));
		int total = service.getTotal(cri);
		log.info("total: " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	@GetMapping(value="/getAttachList",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		@ResponseBody
		public ResponseEntity<List<BoardAttachVO>> getAttachList(Integer bno){
		log.info("getAttachList" + bno);
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	//마이 페이지 안의 판매글 목록
	@GetMapping("/getSale")
	public void get(@RequestParam("bno") Integer bno, Model model) {
		log.info("/getSale");
		model.addAttribute("board", service.get(bno));
	}
	
	//관심목록
	@PostMapping("/addLike")
	@PreAuthorize("isAuthenticated()")
	public void addLike(LikeVO like, RedirectAttributes rttr) {
		log.info("addLike" + like);
		
	}
	
}
