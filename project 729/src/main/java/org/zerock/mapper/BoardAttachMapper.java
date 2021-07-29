package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	public void insert(BoardAttachVO vo);
	public int delete(String uuid);
	public List<BoardAttachVO> findByBno(Integer bno);
	// 파일에서는 update = delete -> insert
	public void deleteAll(Integer bno); // 해당 게시글의 첨부파일 정보를 삭제
	public List<BoardAttachVO> getOldFiles();	// 어제 날짜로 업로드된 첨부파일의 정보를 가져온다.
}
