package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

// CRUD 형태 :  Create(insert), Retrieve(select), Update, Delete
// 데이터 속성에 따라서 조금 차이가 있다....
public interface ReplyMapper {
	public int insert(ReplyVO vo);	// 추가된 게시글의 수가 반환
	public ReplyVO read(Integer rno);
	public int delete(Integer rno);
	public int update(ReplyVO reply);
	public List<ReplyVO> getListWithPaging( // 댓글 목록(페이징)
			@Param("cri") Criteria cri, @Param("bno") Integer bno);
	public int getCountByBno(Integer bno);	// 댓글의 갯수를 구하는 매소드

}
