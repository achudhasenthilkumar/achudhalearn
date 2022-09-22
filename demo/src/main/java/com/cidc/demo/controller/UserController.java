package com.cidc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cidc.demo.entity.ResponseVO;
import com.cidc.demo.entity.UserVO;
import com.cidc.demo.response.CustomResponse;
import com.cidc.demo.service.JwtService;
import com.cidc.demo.service.UserService;
import com.cidc.demo.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


//import com.cidc.Jwt.JwtUtilOne;

@RestController
@RequestMapping(value = "/Users")
public class UserController extends CustomResponse {

	@Autowired
	UserService userService;

	@Autowired
	JwtService jwtService;

	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/restapi")
	public ResponseVO restapi() {
		return super.generateResponse("Successfully inserted all datas !", HttpStatus.OK, userService.getUser());
	}

	@GetMapping("/userDetail")
	public ResponseVO getuser() {
		return super.generateResponse("Successfully get all user datas!", HttpStatus.OK, userService.getUserdetails());
	}

	@GetMapping("/user")
	public ResponseVO getsingleuser(int id) {
		return super.generateResponse("Successfully get a single user data!", HttpStatus.OK,
				userService.getsingleUser(id));
	}

	@PutMapping("/updateuser")
	public ResponseVO updateUser(@RequestBody UserVO obj) {
		return super.generateResponse("Successfully updated a user data!", HttpStatus.OK,
				userService.updateEmployee(obj));
	}

	@DeleteMapping("/delete")
	public ResponseVO delete() {
		return super.generateResponse("Successfully deleted a user data!", HttpStatus.OK, userService.delete());

	}

	@DeleteMapping("/deleteuser")
	public ResponseVO deleteUser(Integer id) throws Exception {
		return super.generateResponse("Successfully deleted a single user data!", HttpStatus.OK,
				userService.deleteUser(id));
	}

	@PostMapping("/newuser")
	public ResponseVO newUser(@RequestBody UserVO obj) {
		return super.generateResponse("Successfully added a new user!", HttpStatus.OK, userService.CreateUser(obj));
	}

	@GetMapping("/page")
	public ResponseVO getUser(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "6") Integer pageSize, String sortBy) {
		return super.generateResponse("Sucessfully get the page", HttpStatus.OK,
				userService.getAllUser(pageNo, pageSize, sortBy));
	}

	@PostMapping("/login")
	public ResponseVO jwt(@RequestBody UserVO obj) {
		return jwtService.Jwt(obj);
	}

	@GetMapping("/auth")
	public ResponseVO getauth(@RequestHeader String Authorization) throws JsonMappingException, JsonProcessingException {
		return super.generateResponse("Sucess", HttpStatus.OK,userService.getAuth(Authorization));
	}
}