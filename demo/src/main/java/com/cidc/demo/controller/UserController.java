package com.cidc.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.cidc.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/*
 * Controller class of all API
 * Method will run and return the HttpResponse in CustomeResponse
 */
@RestController
@RequestMapping(value = "/Users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/restapi")
	public JSONObject restapi() throws JsonMappingException, JsonProcessingException {
		return userService.getUser();
	}

	@GetMapping("/userDetail")
	public ResponseVO getuser() throws JsonMappingException, JsonProcessingException {
		return userService.getUserdetails();
	}

	@GetMapping("/user")
	public ResponseVO getsingleuser(int id,HttpServletResponse response) throws IOException {
		return userService.getsingleUser(id,response);
	}

	@PutMapping("/updateuser")
	public ResponseVO updateUser(@RequestBody UserVO obj) throws JsonMappingException, JsonProcessingException {
		return userService.updateEmployee(obj);
	}

	@DeleteMapping("/delete")
	public ResponseVO delete() throws JsonMappingException, JsonProcessingException {
		return userService.delete();

	}

	@DeleteMapping("/deleteuser")
	public ResponseVO deleteUser(Integer id) throws Exception {
		return userService.deleteUser(id);
	}

	@PostMapping("/newuser")
	public ResponseVO newUser(@RequestBody UserVO obj) throws JsonMappingException, JsonProcessingException {

		return userService.CreateUser(obj);
	}

	@GetMapping("/page")
	public ResponseVO getUser(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "6") Integer pageSize, String sortBy) throws JsonMappingException, JsonProcessingException {
		return userService.getAllUser(pageNo, pageSize, sortBy);
	}

	@GetMapping("/authenticateUser")
	public ResponseVO getauth(@RequestHeader String Authorization)
			throws JsonMappingException, JsonProcessingException {
		return userService.getAuth(Authorization);
	}
}