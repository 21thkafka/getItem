package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.MemberVO;

public interface MemberMapper {
	public MemberVO read(String userid);	//회원 정보 가져오기
	public void join(MemberVO member); //회원가입
	public String idCheck(String userid);
	public MemberVO userInfo(String userid);	//마이페이지
	public List<BoardVO> saleInfo(String userid);		// 판매내역
	
}
