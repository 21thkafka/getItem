package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	// 게시글에 대한 댓글을 조작을 편하게 하기 위해 배열
	private Integer[] bnoArr = {917484, 917483, 917482, 917481, 917470};
	@Setter(onMethod_ = @Autowired)	//Mapper를 주입받음
	private ReplyMapper mapper;
	
//	@Test
	public void testMapper() {
		log.info(mapper); 			//Mapper가 주입되어 사용 가능한지 테스트
	}
	
//	@Test
	public void testInsert() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트" + i);
			vo.setReplyer("replyer" + i);
			mapper.insert(vo);
		});
	}
	
//	@Testd
	public void testRead() {
		Integer targetRno = 5;
		ReplyVO vo = mapper.read(targetRno);
		log.info(vo);
	}
	
//	@Test
	public void testDelete() {
		Integer targetRno = 5;		 // 존재하는 rno를 사용
		mapper.delete(targetRno);
	}
	
//	@Test
	public void testUpdate() {		// 존재하는 rno를 사용
		Integer targetRno = 10;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("UpdateReply");
		int count = mapper.update(vo);
		log.info("Update Counr: " + count);
	}
	
//	@Test
	public void testList() {
		Criteria cri = new Criteria();
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		replies.forEach(reply -> log.info(reply));
	}
	@Test
	public void testList2() {
		Criteria cri = new Criteria(2, 10);
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		replies.forEach(reply -> log.info(reply));
	}
}
