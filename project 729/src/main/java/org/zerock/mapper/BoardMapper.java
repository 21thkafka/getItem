package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.LikeVO;


// 2가지 방법
// 방법 1 : 자바 인터페이스에서 SQL을 작성하는 방법
// 방법 2 : 자바 인터페이스 + XML mapper(SQL)을 사용하는 방법
public interface BoardMapper {
//	@Select("select * from tbl_board where bno > 0 order by bno desc")
	public List<BoardVO> getList();
	
	public void insertSelectKey(BoardVO board);
	
	public void insert(BoardVO board);
	
	public BoardVO read(Integer bno);
	
	public int delete(Integer bno);
	
	public int update(BoardVO board);
	
	public List<BoardVO> getListWithPaging(Criteria cri);	// 페이징 처리를 위한 게시글 목록 가져오기
	
	public int getTotalCount(Criteria cri);
	
	// 댓글이 추가 (amount=+1), 삭제(amount=-1)되면 replyCnt 값을 갱신하는 메소드
	public void updateReplyCnt(@Param("bno") Integer bno, @Param("amount") int amount);
	
	public void addLike(LikeVO like);

	public boolean soldout(Integer out);
}
