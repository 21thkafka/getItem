package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;
import org.zerock.domain.UploadForm;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	@GetMapping("/uploadForm")		// url : /uploadForm
	public void uploadForm() {
		log.info("uploadForm");
	}	// jsp: /uploadForm.jsp
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(UploadForm form, Model model) {
		log.info("desc=" + form.getDesc());
		String uploadFolder = "C:\\SMH\\upload";
		for(MultipartFile multipartFile : form.getUploadFile()) {
			// IE 브라우저는 파일이름이 전체 경로를 포함하는 버전이 있음
			// C:\User\SHM\다운로드\a.hwp : 필요한 정보는 a.hwp인데
			log.info("---------------------------");
			log.info("Upload file name: " + multipartFile.getOriginalFilename());
			log.info("upload file size: " + multipartFile.getSize());
			
			//파일 저장
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			try {
				multipartFile.transferTo(saveFile);
			} catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	@GetMapping("/uploadAjax")		// url : /uploadForm
	public void uploadAjax() {
		log.info("upload ajax");
	}	//
	

	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/uploadAjaxAction", 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)		// JSON 형태로 응답(Content-Type)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("upload ajax post....");
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();	// 반환될 목록
		String uploadFolder = "C:\\SMH\\upload";

		// 업로드 폴더 생성
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath); //yyyy/MM/dd
		log.info("upload path: " + uploadPath);
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();	// dd 경로까지의 중간 폴더들을 모두 생성
		}		// make yyyy/MM/dd folder
		
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			log.info("---------------------------");
			log.info("Upload file name: " + multipartFile.getOriginalFilename());
			log.info("upload file size: " + multipartFile.getSize());
			
			// IE 브라우저는 파일이름이 전체 경로를 포함하는 버전이 있음->파일 정보만을 얻으려면 약간의 조작이 필요
			// C:\User\SHM\다운로드\a.hwp : 필요한 정보는 a.hwp인데
			//IE has file path -> 경로 자르기
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			log.info("only file name: " + uploadFileName);
			attachDTO.setFileName(uploadFileName);	// 브라우저에서 첨부한 파일 이름
			
			// 파일이름 중복 방지
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			//파일 저장
			File saveFile = new File(uploadPath, uploadFileName);
			try {
				multipartFile.transferTo(saveFile);	// 원본 파일을 저장
				attachDTO.setUuid(uuid.toString());			//uuid
				attachDTO.setUploadPath(uploadFolderPath);	// yyyy/MM/dd 폴더 구조
				// 섬네일을 저장
				// 이미지 파일 유형인지 검사
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);	// 이미지인 경우 true로 저장
					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(
							multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				list.add(attachDTO);
			} catch(Exception e) {
				log.error(e.getMessage());
			}
		}	// url : uploadAjaxAction.jsp 응답 -> 404 Not Fount
		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
	}
	
	
	// 2021/06/16 형태의 문자열을 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	// 첨부 파일이 이미지 타입인지를 검사 : Content-Type이 "image/*"인지를 검사
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			if(contentType != null) // modified by SMH
				return contentType.startsWith("image");
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	//파일 표시하기 위해
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName : " + fileName);
		File file = new File("C:\\SMH\\upload\\" + fileName);
		log.info("file: " + file);
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			// Content-Type에 이미지(image/jpg, image/png, image/gif) MIME을 실어주면 이미지로 간주
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(
					FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			// FileCopyUtils.copyToByteArray() : 파일을 읽어서 바이트 배열로 반환
		} catch (IOException e){
			e.printStackTrace();
		}
		return result;
	}
	@GetMapping(value="/download",
	produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	// 브라우저를 식별하기 위해서 Header중의 User-Agent를 사용
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		log.info("downloadfile : " + fileName);
		Resource resource = new FileSystemResource("C:\\SMH\\upload\\" + fileName);
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		log.info("resource: " + resource);
		String resourceName = resource.getFilename();
		
		// remove UUID
		resourceName = resourceName.substring(resourceName.indexOf("_")-1);
		
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadName = null;	// 브라우저별로 한글이름처리를 다르게 하는 것을 고려
			if(userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceName,
						"UTF-8").replaceAll("\\+", " ");
				
			}else if(userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceName, "UTF-8");
				log.info("Edge name: " + downloadName);
			} else {
				log.info("Chrome browser");	// 인코딩
				downloadName = new String(resourceName.getBytes("UTF-8"),
						"ISO-8859-1");
			}
			log.info("downloadName: " + downloadName);
			headers.add("Content-Disposition", 
					"attachment; filename="+ downloadName);
			
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	   public ResponseEntity<String> deleteFile(String fileName, String type) {
	      log.info("deleteFile: " + fileName);
	      File file;
	      try {
	         file = new File("C:\\SMH\\upload\\" + URLDecoder.decode(fileName,
	               "UTF-8"));
	         file.delete();            // 원본 파일 삭제
	         if (type.equals("image")) {   // 섬네일 이미지 삭제
	            String largeFileName = file.getAbsolutePath().replace("s_", "");
	            log.info("largeFileName: " + largeFileName);
	            file = new File(largeFileName);
	            file.delete();
	         }
	      } catch (UnsupportedEncodingException e) {
	         e.printStackTrace();
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	      }
	      return new ResponseEntity<String>("deleted", HttpStatus.OK);
	   }
}
