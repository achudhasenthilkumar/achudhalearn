package com.cidc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Users")

public class UserController extends CustomResponse {

	@Autowired
	private UserService userService;
	
	@GetMapping("/restapi")
	public ResponseVO restapi(String email) {
		return super.generateResponse("Successfully inserted all datas !", HttpStatus.OK, userService.getUser(email));
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
//	@GetMapping("/Schedule")
//	public String Schedule()
//	{
//		return userService.fixedDelaySch();
//	}
}
