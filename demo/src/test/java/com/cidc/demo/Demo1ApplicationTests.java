package com.cidc.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cidc.demo.controller.UserController;
import com.cidc.demo.repository.UserRepository;


@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
@SpringBootTest
class Demo1ApplicationTests {

	@Autowired
	UserController userController;

	@Autowired
	UserRepository userRepository;
//
//	MockMvc mvc;
//
//	@Test
//	public void deleteEmployeeAPI() throws Exception
//	{
//	  this.mvc.perform( MockMvcRequestBuilders.delete("/Users/deleteuser/{id}",1183));
//	}
	@Test
	public void deleteUser()
	{
		userRepository.deleteById(1180);
		assertThat(userRepository.existsById(1180)).isFalse();
	}
}