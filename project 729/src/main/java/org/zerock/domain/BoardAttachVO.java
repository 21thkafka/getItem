package org.zerock.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	private String uuid;		// PK
	private String uploadPath;	// yyyy/MM/dd
	private String fileName;	// 업로드한 파일 이름
	private boolean fileType;	// 이미지 여부
	private Integer bno;		// 게시글 번호(FK)
}
