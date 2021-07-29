package org.zerock.task;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class FileCheckTask {
	@Setter(onMethod_ = {@Autowired})
	private BoardAttachMapper attachMapper;
	

	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
	
	@Scheduled(cron="0 00 12 * * *")
	// @Scheduled : 주기적인 작업을 등록하는 어노테이션
	public void checkFiles() throws Exception{
		log.warn("File Check Task run....");
		log.warn("===========================");
		//데이터베이스 파일 리스트
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
	

	// 주기적으로 작업함
	
	//존재하는 파일 목록
		List<Path> fileListPaths = fileList.stream().map(vo->Paths.get("D:\\SMH\\upload", 
				vo.getUploadPath(), vo.getUuid()+"_"+ vo.getFileName())).collect(Collectors.toList());
		
		//썸네일 이미지 처리
		fileList.stream().filter(vo->vo.isFileType()== true).map(vo->Paths.get("D:\\SMH\\upload",
				vo.getUploadPath(),"s_"+vo.getUuid()+"_"+vo.getFileName())).forEach(p->fileListPaths.add(p));
		log.warn("====================================================");
		fileListPaths.forEach(p->log.warn(p));
		
		//디렉토리
		File targetDir = Paths.get("D:\\zzz\\upload", getFolderYesterDay()).toFile();
		File[] removeFiles = targetDir.listFiles( file -> fileListPaths.contains(file.toPath()) == false);
		
		if(removeFiles == null)
			return;
		
		log.warn("--------------------");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}
}
