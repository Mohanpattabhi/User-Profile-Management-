package com.upm.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.upm.model.UserImages;
import com.upm.service.LoginService;
import com.upm.util.Constants;

/*
 * Handles requests for upload and download photos
 */
@Controller
public class PhotoUploadController {

	private LoginService loginService;

	@Autowired(required = true)
	@Qualifier(value = "loginService")
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(PhotoUploadController.class);

	/*
	 * Redirects to upload Photo page
	 */
	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.GET)
	public String uploadPhoto(Model model,
			@RequestParam("loginId") String loginId) {
		model.addAttribute("loginId", loginId);
		model.addAttribute("fromPage", "login");
		return "uploadPhoto";
	}

	/*
	 * Upload photo for a particular loginID
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(HttpServletRequest request,
			@RequestParam("file") MultipartFile file, Model model) {
		String message = Constants.EMPTY_STRING;
		boolean exceptionOccured = false;
		String loginIdValue = request.getParameter("loginId");
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				loginService.storeImage(bytes, loginIdValue);

			} catch (Exception e) {
				message = "Some problem in photo upload";
				exceptionOccured = true;
			}
		}

		if (!exceptionOccured) {
			message = "Photo has been uploaded";
		}
		model.addAttribute("fromPage", "upload");
		model.addAttribute("photo_status_message", message);
		return "uploadPhoto";
	}

	/*
	 * Downloads the photos for a particular user
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public @ResponseBody String downloadFile(@RequestParam("name") String name,
			HttpServletResponse response) {

		try {
			UserImages userImage = loginService.getUserImage(name);
			String mimeType = "application/octet-stream";
			byte[] imageData = userImage.getImage();

			OutputStream outStream = response.getOutputStream();

			outStream.write(imageData);

			outStream.close();

			System.out.println("Server File Location=");
		} catch (Exception e) {
			return "You failed to upload " + name + " => " + e.getMessage();
		}

		return "displayPhoto";
	}

}
