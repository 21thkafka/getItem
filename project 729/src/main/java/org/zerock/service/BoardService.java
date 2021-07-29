package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.LikeVO;

public interface BoardService {
	public void register(BoardVO board);	// 게시글 등록
	public BoardVO get(Integer bno);		// 게시글 상세 보기
	public boolean modify(BoardVO board);	// 게시글 수정
	public boolean remove(Integer bno);		// 게시글 삭제
//	public List<BoardVO> getList();			// 게시글 목록보기
	public List<BoardVO> getList(Criteria cri);
	public int getTotal(Criteria cri);
	
	public List<BoardAttachVO> getAttachList(Integer bno);	// 첨부파일 정보를 가져오기
	
	public void addLike(LikeVO like);	// 관심목록 추가
	
	public boolean soldout(Integer out);
}
