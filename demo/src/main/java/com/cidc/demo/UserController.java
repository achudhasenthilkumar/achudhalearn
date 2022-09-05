package com.cidc.demo;

import java.util.Collection;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	public JSONObject restapi() throws Exception {
		
		return userService.getuser();
	}
	@GetMapping("/userDetail")
	public Collection<User> getuser()
	{
		return userService.getUserdetails();
	}
	@GetMapping("/user")
	public Optional<User> getsingleuser(int id)
	{
		return userService.getsingleUser(id);
	}
	@PutMapping("/updateuser")
	public User updateUser(@RequestBody UserVO obj)
	{
		return userService.updateEmployee(obj);
	}
	@DeleteMapping("/delete")
	public String deleteUser()
	{
		return null;
		
	}
}
