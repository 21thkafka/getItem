package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Integer bno;
	private String title;
	private String content;
	private String userid;
	private String price;
	private String saledate;
	private Date regDate;		// MySql : datetime(기간의 범위가 더 넓음)
	private Date updateDate;	// timestamp 2037년까지
	
	private int replyCnt;		// 댓글의 수
	
	private List<BoardAttachVO> attachList;	//첨부파일 정보
	
	private boolean soldout; 	// 판매중 or 판매완료 정보
}
