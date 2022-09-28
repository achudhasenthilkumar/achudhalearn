package com.cidc.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cidc.demo.controller.JwtController;
import com.cidc.demo.controller.UserController;
import com.cidc.demo.entity.UserVO;
import com.cidc.demo.repository.UserRepository;
import com.cidc.demo.request.JwtRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class Demo1ApplicationTests {

	@Autowired
	UserController userController;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MockMvc mvc;

	@Autowired
	JwtController jwtController;

	String BasicAuth="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJldmUuaG9sdEByZXFyZXMuaW4iLCJleHAiOjE2NjQ0MDc4NTksImlhdCI6MTY2NDM3MTg1OX0.-V7pwXjgxJZdSZFAURqru52PqSgohtdgN1iY3ErxlCmVMPc3AtgLu6TuZHKn11Fspv_ME5Z8IMKen__iagPWAw";

	@Test
	public void getUser() throws Exception
	{

		MvcResult resultAction= mvc.perform(MockMvcRequestBuilders.get("/Users/user",1184)
				  .contentType(MediaType.APPLICATION_JSON))
				  .andExpect(MockMvcResultMatchers.status().isOk())
				  .andReturn();

		System.out.println(resultAction);
		assertNotNull(resultAction);


//	 MvcResult resultAction=mvc.perform(MockMvcRequestBuilders.get("/Users/user/{id}",1184)
//			 .contentType(MediaType.APPLICATION_JSON)
//			 .accept(MediaType.APPLICATION_JSON)
//			 .content(obj.readTree(obj)))
//			 .andExpect(MockMvcResultMatchers.status().isOk())
//			 .andReturn();
//	 String aa=resultAction.getResponse().getContentAsString();
//	 assertNotNull(aa);
	}


	@Test
	public void deleteEmployeeAPI() throws Exception
	{
//	  this.mvc.perform( MockMvcRequestBuilders.delete("/Users/deleteuser/{id}",1184));

	  mvc.perform(MockMvcRequestBuilders.delete("/Users/deleteuser",1184)
	  .contentType(MediaType.APPLICATION_JSON))
	  .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void updateUser() throws JsonProcessingException, Exception
	{
		UserVO obj=new UserVO();
		obj.setId(1179);
		obj.setEmail("george.bluth@reqres.in");
		obj.setFirst_name("vijay");
		obj.setLast_name("achudha");
		obj.setPassword("password");
		obj.setAvatar("http://");

		ObjectMapper mapper=new ObjectMapper();

		MvcResult resultActions = this.mvc.perform(MockMvcRequestBuilders.put("/Users/updateuser",obj)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.header(HttpHeaders.AUTHORIZATION, BasicAuth)
		.content(mapper.writeValueAsString(obj)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();

		System.out.println(resultActions.getResponse().getContentAsString());
		assertNotNull(resultActions.getResponse().getContentAsString());
	}

	@Test
	public void generateToken() throws Exception {

		JwtRequestModel request=new JwtRequestModel();

		request.setEmail("eve.holt@reqres.in");
		request.setPassword("password");

		ObjectMapper mapper=new ObjectMapper();

		MvcResult resultActions = this.mvc.perform(MockMvcRequestBuilders.post("/login",request)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String aa=resultActions.getResponse().getContentAsString();
		System.out.println(aa);
		assertNotNull(resultActions.getResponse().getContentAsString());
//		getAllUser(aa);

	}

//	@Test
//	public void validateToken()
//	{
//		JwtResponseModel jwtResponseModel=new JwtResponseModel();
//		String aa=jwtResponseModel.getToken();
//		System.out.println(aa);
//		assertNotNull(aa);
//	}
//
	 @Test
	 public RequestBuilder getAllUser(String aa) {
		        return MockMvcRequestBuilders
	            .get("/Users/userDetail",aa)
	            .accept(MediaType.APPLICATION_JSON)
	            .header(HttpHeaders.AUTHORIZATION, BasicAuth)
	            .contentType(MediaType.APPLICATION_JSON);
	    }
}
