package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private String username;
	private String userid;
	private String password;
	private String adress;
	private int warning;
	private boolean enabled;	// 사용자 사용 여부를 제한 설정
	
	private Date regDate;

	private List<AuthVO> authList;	//권한 목록
	

}
