package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

//	기능에 대한 처리 : 서비스 로직
// Mapper와 1:1로 대응되는 편이지만 꼭 그런 것만은 아니다.
public interface ReplyService {
	public int register(ReplyVO vo);
	public ReplyVO get(Integer rno);
	public int modify(ReplyVO vo);
	public int remove(Integer rno);
	public List<ReplyVO> getList(Criteria cri, Integer bno);
	public ReplyPageDTO getListPage(Criteria cri, Integer bno);
}
