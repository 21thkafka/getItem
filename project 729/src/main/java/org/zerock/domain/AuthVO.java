package org.zerock.domain;

import lombok.Data;

@Data
public class AuthVO {
	private String userid; 	// 사용자 아이디
	private String auth;//사용자 권한
}
