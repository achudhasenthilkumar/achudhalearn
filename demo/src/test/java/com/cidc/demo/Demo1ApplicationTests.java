package com.cidc.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cidc.demo.controller.JwtController;
import com.cidc.demo.controller.UserController;
import com.cidc.demo.entity.UserVO;
import com.cidc.demo.request.JwtRequestModel;
import com.cidc.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class Demo1ApplicationTests {

	@Autowired
	UserController userController;

	@Autowired
	MockMvc mvc;

	@MockBean
	UserService userService;

	@Autowired
	JwtController jwtController;

	@Test
	@WithMockUser(username = "https://reqres.in/img/faces/2-image.jpg", password = "password")
	public void getUser() throws Exception {

		String num = "1174";
		ResultActions mvcResult = mvc.perform(MockMvcRequestBuilders.get("/Users/user").param("id", num))
				.andExpect(MockMvcResultMatchers.status().isOk());
		assertNotNull(mvcResult.andExpect(MockMvcResultMatchers.status().isOk()));
	}

	@Test
	@WithMockUser(username = "https://reqres.in/img/faces/2-image.jpg", password = "password")
	public void getUserNeg() throws Exception {

		String num = "2000";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/Users/user")
				.param("id", num))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		System.out.println(MockMvcResultMatchers.status());
		assertEquals(404,mvcResult.getResponse().getStatus());
	}

	@Test
	@WithMockUser(username = "https://reqres.in/img/faces/2-image.jpg", password = "password")
	public void getAllUser() throws Exception {
		ResultActions mvcResult = mvc.perform(MockMvcRequestBuilders.get("/Users/userDetail"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		assertNotNull(mvcResult.andExpect(MockMvcResultMatchers.status().isOk()));
	}

	@Test
	@WithMockUser(username = "https://reqres.in/img/faces/2-image.jpg", password = "password")
	public void deleteEmployeeAPI() throws Exception {

		String num = "1174";
		ResultActions mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/Users/deleteuser").param("id", num))
				.andExpect(MockMvcResultMatchers.status().isOk());
		assertNotNull(mvcResult.andExpect(MockMvcResultMatchers.status().isOk()));
	}

	@Test
	@WithMockUser(username = "https://reqres.in/img/faces/2-image.jpg", password = "password")
	public void deleteAllEmployee() throws Exception {

		ResultActions mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/Users/delete"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		assertNotNull(mvcResult.andExpect(MockMvcResultMatchers.status().isOk()));
	}


	@Test
	public void generateToken() throws Exception {

		JwtRequestModel request = new JwtRequestModel();
		request.setEmail("eve.holt@reqres.in");
		request.setPassword("password");
		ObjectMapper mapper = new ObjectMapper();
		MvcResult resultActions = this.mvc
				.perform(MockMvcRequestBuilders.post("/login", request).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		assertNotNull(resultActions.getResponse().getContentAsString());
	}

	@Test
	public void newUser() throws Exception {

		UserVO userVO = new UserVO();
		userVO.setEmail("george.bluth@reqres.in");
		userVO.setFirst_name("vijay");
		userVO.setLast_name("achudha");
		userVO.setPassword("Users@01");
		userVO.setAvatar("http://oknew");

		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult mvcResult = this.mvc
				.perform(MockMvcRequestBuilders.post("/Users/newuser")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userVO)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void updateUser() throws JsonProcessingException, Exception {

		JwtRequestModel request = new JwtRequestModel();
		request.setEmail("eve.holt@reqres.in");
		request.setPassword("password");
		ObjectMapper mapper = new ObjectMapper();
		MvcResult resultActions = this.mvc
				.perform(MockMvcRequestBuilders.post("/login", request).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String tokennew = resultActions.getResponse().getContentAsString();
		JsonNode jsonNode = mapper.readTree(tokennew);
		String token = jsonNode.findValue("token").asText();

		UserVO userVO = new UserVO();
		userVO.setId(1179);
		userVO.setEmail("george.bluth@reqres.in");
		userVO.setFirst_name("vijay");
		userVO.setLast_name("achudha");
		userVO.setPassword("password");
		userVO.setAvatar("http://oknew");

		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult mvcResult = this.mvc
				.perform(MockMvcRequestBuilders.put("/Users/updateuser").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(userVO)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		assertNotNull(mvcResult.getResponse().getContentAsString());
	}
}