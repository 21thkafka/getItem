package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	private int rno;	// 댓글의 번호
	private int bno;	// 게시글 번호
	private String reply;	// 댓글의 내용
	private String replyer; // 작성자
	private Date replyDate;	// 작성일
	private Date updateDate; // 수정일
}
