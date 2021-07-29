package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.MemberVO;

public interface MemberService {
	
	public void join(MemberVO member);

	public String idCheck(String userid);

	public MemberVO userInfo(String userid);
	
	public List<BoardVO> saleInfo(String userid);

}
