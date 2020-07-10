package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}

	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {

		String uploadFolder = "C:\\upload";

		for (MultipartFile multipartFile : uploadFile) {

			log.info("-------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());

			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}

	/*@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
	
		log.info("update ajax post.........");
	
		String uploadFolder = "C:\\upload";
	
		for (MultipartFile multipartFile : uploadFile) {
	
			log.info("-------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
	
			String uploadFileName = multipartFile.getOriginalFilename();
	
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("only file name: " + uploadFileName);
	
			File saveFile = new File(uploadFolder, uploadFileName);
	
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}*/

	/**
	 * 년/월/일 폴더 생성
	 * 
	 * @return
	 */
	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	/**
	 * 이미지 파일 체크
	 * 
	 * @param file
	 * @return
	 */
	private boolean checkImageType(File file) {

		try {

			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 1.중복된 이름의 첨부파일 처리 
	 * 2.년/월/일 폴더 생성 
	 * 3.중복 방지를 위한 UUID 적용 
	 * 4.섬네일 이미지 생성
	 * 5.업로드된 파일의 정보 반환
	 * 
	 * @param uploadFile
	 * @return
	 */
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";

		String uploadFolderPath = getFolder();

		// 년/월/일 폴더의 생성
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {

			AttachFileDTO attachDTO = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename();

			// IE의 경우에는 전체 파일 경로가 전송되므로, 마지막에 \\를 기준으로 잘라낸 문자열이 실제 파일 이름
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

			// 원본 파일의 이름
			attachDTO.setFileName(uploadFileName);

			// 중복 방지를 위한 UUID 적용
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {

				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);

				// UUID 값
				attachDTO.setUuid(uuid.toString());
				// 업로드 경로
				attachDTO.setUploadPath(uploadFolderPath);

				// 이미지 파일 체크
				if (checkImageType(saveFile)) {

					// 이미지 여부
					attachDTO.setImage(true);

					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));

					// Thumbnailator는 InputStream과 java.io.File 객체를 이용해서 파일을 생성할 수 있고, 뒤에 사이즈에 대한 부분을 파라미터로 witdh와 height를 지정할 수 있음
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

					thumbnail.close();
				}

				list.add(attachDTO);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}