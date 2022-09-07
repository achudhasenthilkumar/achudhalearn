package com.cidc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/Users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository UsersRepository;

	@GetMapping("/restapi")
	public ResponseEntity<Object> restapi(){
		return CustomResponse.generateResponse("Successfully added data!",HttpStatus.OK,userService.getUser());
	}
	@GetMapping("/userDetail")
	public ResponseEntity<Object> getuser()
	{
		return CustomResponse.generateResponse("Successfully get all user datas!", HttpStatus.OK,userService.getUserdetails());
	}
	@GetMapping("/user")
	public ResponseEntity<Object> getsingleuser(int id)
	{
		return CustomResponse.generateResponse("Successfully get a single user data!", HttpStatus.OK,userService.getsingleUser(id));
	}
	@PutMapping("/updateuser")
	public ResponseEntity<Object> updateUser(@RequestBody UserVO obj)
	{
		
		return CustomResponse.generateResponse("Successfully updated a user data!", HttpStatus.OK,userService.updateEmployee(obj));
	}
	@DeleteMapping("/delete")
	public ResponseEntity<Object> delete()
	{
		
		return CustomResponse.generateResponse("Successfully deleted a user data!", HttpStatus.OK,userService.delete());
		
	}
	@DeleteMapping("/deleteuser")
	public ResponseEntity<Object> deleteUser(Integer id) throws Exception
	{
		
		return CustomResponse.generateResponse("Successfully deleted a single user data!", HttpStatus.OK,userService.deleteUser(id));
	}
	@PostMapping("/newuser")
	public ResponseEntity<Object>newUser(@RequestBody UserVO obj){
		return CustomResponse.generateResponse("Successfully added a new user!",HttpStatus.OK,userService.CreateUser(obj));
	}
}
