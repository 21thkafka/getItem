package org.zerock.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadForm {
	String desc;				// 입력받는 정보
	MultipartFile[] uploadFile;	// 첨부파일
}
