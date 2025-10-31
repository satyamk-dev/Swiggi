package com.nt.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nt.entityDTO.RequestDTO;
import com.nt.entityDTO.ResponseDTO;
import com.nt.service.ICommentListService;
import com.nt.service.ICustomerService;
import com.nt.utility.Constant;
import com.nt.utility.ResponseMessage;

/**
 * This controller handles HTTP requests related to user management.
 * <p>
 * It provides endpoints for user registration, fetching user details by ID,
 * saving or updating user data, and updating existing records.
 *
 * @author Satyam Kumar
 * @version 1.0
 * @since 2025-10-15
 */

@RestController
@RequestMapping("/swiggi")
@EnableCaching
public class SwiggiController {
	@Autowired
	private  ICustomerService customerService;

	@Autowired 
	private ICommentListService comments;
	
	private static final Logger logger = LoggerFactory.getLogger(SwiggiController.class);

	
	
	
	 
	// Excel File data Save to Data Base
   @PostMapping("/saveexcelfile")
	public ResponseEntity<ResponseMessage> ExcelSaveDatabase(MultipartFile file) {
	   logger.info(" Request POST: /saveexcelfile for saving bulk file :{} ", file);
		if (file.isEmpty() || file == null) {
			logger.warn("File is Empty or Null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constant.FAILED, " File is Empty"));
		}else {
			try {
				comments.BulkCommentData(file);
				logger.debug("bulk file saved sucessfully file: {}", file.getOriginalFilename());
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpURLConnection.HTTP_ACCEPTED,Constant.SUCCESS,"Excel file Save To Data Base SuccessFully"));
				
			} catch (Exception e) {
				logger.error("Exception comming : {} ",e);
				e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constant.FAILED,
					"File Have Some Problem"));
		}
	}

   }
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<ResponseDTO>> AllUsersDataList() {
		logger.info("Request Received : GET /getAllUsers");
		List<ResponseDTO> fetchAllData = customerService.FetchAllData();
		logger.debug("Fatched {} users from database  ", fetchAllData.size());
		return ResponseEntity.ok(fetchAllData);
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseDTO> RegisterUsers(@RequestBody RequestDTO requestDto) {
		logger.info("Request for Register User : POST /register");
		ResponseDTO registerCustomer = customerService.RegisterCustomer(requestDto);
		logger.debug("Register user Data From DataBase : {}", registerCustomer);
		return ResponseEntity.ok(registerCustomer);
	}

	@GetMapping("/findbyuserid/{id}")
	public ResponseEntity<ResponseDTO> GetDataById(@PathVariable Long id) {
		logger.info("Request for finding user by Id: GET /findbyuserid/{id} ");
		ResponseDTO fetchById = customerService.FetchById(id);
		logger.debug("Response Data from Database by id : {}", fetchById);
		return ResponseEntity.ok(fetchById);
	}

	@PostMapping("/saveorupdate")
	public ResponseEntity<String> SaveOrUpdate(@RequestBody RequestDTO requestDto) {
		logger.info("Requst for Save and Update way one method POST /saveorupdate");
		String saveAndUpdate = customerService.SaveAndUpdate(requestDto);
		logger.debug("Get Response message from service layer : {}", saveAndUpdate);
		return ResponseEntity.ok(saveAndUpdate);
	}

	@PutMapping("updateuser/{id}")
	public ResponseEntity<String> UpdateUser(@PathVariable Long id, @RequestBody RequestDTO requestDto) {
		logger.info("Request for Update User data PUT /updateUser/{id}");
		String userDetailUpdate = customerService.UserDetailUpdate(id, requestDto);
		logger.debug("Response message from service layer: {} ", userDetailUpdate);
		return ResponseEntity.ok(userDetailUpdate);
	}

	@GetMapping("/all-users-pages")
	public ResponseEntity<Page<ResponseDTO>> ShowAllDataPageByPage(@RequestParam int page, @RequestParam int size,
			@RequestParam String columnName, @RequestParam String sortOrder) {
		logger.info("Request parameters page: {} , sizw: {} , columnName: {} ,sortOrder: {}", page, size, columnName,
				sortOrder);
		logger.info("Requesting user list in Page by Page GET /all-users-pages");
		Page<ResponseDTO> fetchPageByPage = customerService.FetchPageByPage(page, size, columnName, sortOrder);

		logger.debug("Getting All Users list page by page: {}", fetchPageByPage);
		return ResponseEntity.ok(fetchPageByPage);
	}

}
