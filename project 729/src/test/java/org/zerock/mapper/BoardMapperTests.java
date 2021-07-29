package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// Mapper가 제대로 동작하는지 유니트 테스트를 수행
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
//	@Test
	public void testGetList() {
		mapper.getList().forEach(board->log.info(board));
	}
//	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setUserid("newbie");
		board.setPrice("5000원");
		board.setSaledate("2021-08-11");
		mapper.insert(board);
		log.info(board);	// 데이터베이스를 검색하지 않는한 bno가 무엇인지 알 수 없다
	}
//	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setUserid("newbie");
		board.setPrice("5000원");
		board.setSaledate("2021-08-11");
		mapper.insertSelectKey(board);
		log.info(board);	// bno값이 무엇인지 알 수 있음 -> 다른 곳에 사용할 수 있음
	}
//	
	@Test
	public void testRead() {
		// 존재하는 게시글 번호를 사용
		BoardVO board = mapper.read(10);
		log.info(board);
	}
	
//	@Test
	public void testDelete() {	// 삭제된 열의 수가 반환
		int result = mapper.delete(12);
		log.info("게시글 삭제 : " + result);
	}
	
//	@Test
	public void testUpdate() {
		BoardVO vo = new BoardVO();
		vo.setBno(10);	// 존재하는 게시글 을 사용
		vo.setTitle("새로 작성하는 글");
		vo.setContent("새로 작성하는 글 수정");
		int count = mapper.update(vo);
		log.info("업데이트 count = " + count);
	}
	
//	@Test
	public void testPaging2() {
		Criteria cri = new Criteria(1, 10);
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board.getBno()));
	}
	
	@Test
	public void testSearch() {
		Criteria cri= new Criteria();
		cri.setKeyword("책");
		cri.setType("TCU");  //TCW: Title, Content, Writer
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
}
