package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class LikeVO {
	private Integer lno;
	private String userid;
	private Integer bno;
	private Date addDate;
}
