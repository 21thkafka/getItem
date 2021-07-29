package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.LikeVO;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
@Service	// Controller에서 주입을 받아야 하므로 스프링에 주입받는 대상임을 알린다.
@Log4j
@AllArgsConstructor		// 생성자로 주입을 받음
public class BoardServiceImpl implements BoardService {
	// Mapper와 연동하기 위해서 BoardMapper를 주입받는다.
	private BoardMapper mapper;
	private BoardAttachMapper attachMapper;	// 교안: Setter로 주입, 여기서는 생성자로 주입

	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("register..." + board);
		mapper.insertSelectKey(board);
		// 첨부 파일이 존재하면 첨부파일 정보를 저장한다.
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVO get(Integer bno) {
		log.info("get..." + bno);
		return mapper.read(bno);
	}

	// 게시글 수정 : 첨부파일이 추가/삭제
	// 추가 : 파일까지 추가 (데터베이스도 추가)
	// 삭제 : 파일이 삭제가 되지 않음, 데이터베이스만 삭제가 된다 -> 문제점으로 파일이 삭제가 되지 않으므로
	// 나중에 삭제하는 동작이 수행이 되어야 함 -> 별도로 배치작업으로 진행이 된다.
	@Override
	public boolean modify(BoardVO board) {
		log.info("modify..." + board);
		// 첨부 파일을 변경
		// 해당 게시글의 첨부파일에 대한 데이터베이스를 모두 삭제
		attachMapper.deleteAll(board.getBno());
		boolean modifyResult = mapper.update(board)==1; // 게시글 수정이 성공했을 때만
				//첨부파일 정보를 변경한다.
		if(modifyResult == true && board.getAttachList() != null && board.getAttachList().size() > 0) {
			// 첨부 파일이 존재하면 BoardAttachTable에 추가
			board.getAttachList().forEach(attach ->{	// 남아 있는 첨부파일의 정보만 추가
				attach.setBno(board.getBno());			// 파일과 불일치가 생길 수 있음
				attachMapper.insert(attach);			// 따라서 불일치를 해소하기 위해 별도로 배치작업으로 파일을 삭제한다.
			});
		}
		return modifyResult;	//성공 여부를 반환
	}

	@Transactional
	@Override
	public boolean remove(Integer bno) {
		log.info("remove..." + bno);
		attachMapper.deleteAll(bno);
		return mapper.delete(bno)==1;
	}

/*	@Override
	public List<BoardVO> getList() {
		log.info("getList...");
		return mapper.getList();
	}*/
	
	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList with Paging..." + cri);
		return mapper.getListWithPaging(cri);
	}
	@Override
	public int getTotal(Criteria cri) {
		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	@Override
	public List<BoardAttachVO> getAttachList(Integer bno){
		log.info("get Attach list by bno" + bno);
		return attachMapper.findByBno(bno);
	}
	
	//관심목록
	@Override
	public void addLike(LikeVO like) {
		log.info("addLike..." + like);
	}
	
	// 판매 유무
	@Override
	public boolean soldout(Integer out) {
		return mapper.soldout(out);
	}
}
