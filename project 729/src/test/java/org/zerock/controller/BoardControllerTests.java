package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// 유니테스트를 위하여 가상의 스프링 환경을 만들어 주어야 한다.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
@WebAppConfiguration
public class BoardControllerTests {
	// 가상의 브라우저 환경을 설정해 주어야 한다. 브라우저에서 요청이 올라오는 것
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
//	@Test
	public void testList() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
//	@Test
	public void testRegister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "현정이 팝니다")
				.param("content","광주에 그렇게 가고 싶어서 팝니다")
				.param("writer", "soojin")
				.param("price", "2000원")
				.param("saledate", "2021-08-11"))
				.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
//	@Test
	public void testGet() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/get")
				.param("bno", "3"))	// 데이터베이스에 있는 게시글을 사용
				.andReturn()
				.getModelAndView().getModelMap());	// board 객체
	}
//	@Test
	public void testModify() throws Exception {
	     String resultPage = mockMvc.perform(MockMvcRequestBuilders
	             .post("/board/modify")
	             .param("bno", "3")
	             .param("title", "강현정은 그렇게")
	             .param("content", "광주복귀에 실패했답니다")
	             .param("saledate", "2021-08-11"))
	             .andReturn().getModelAndView().getViewName();
	       log.info(resultPage);
	    }
	@Test
	public void testList2() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "1")		// Criteria를 넣어준다
				.param("amount", "10"))
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
	
//	@Test
	public void testRemove() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/remove")
				.param("bno", "15"))
				.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
}
