package com.cidc.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import antlr.collections.List;
import net.sf.json.JSONObject;
@Service
public class UserService {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	UserRepository userRepository;
	
	public JSONObject getUser()
		{
		String url="https://reqres.in/api/users";
		RestTemplate resttemplate=new RestTemplate();
		JSONObject jsonObject=resttemplate.getForObject(url,JSONObject.class);
		getUser1(jsonObject);
		return jsonObject;
		}
	private List<User> getUser1(JSONObject jsonObject) {
		
		Map<String,User> user = new HashMap<>();
		List<User> userDetails=jsonObject.get("data");
		for(int i=0;i<userDetails.size();i++)
		{
		User user=this.objectMapper.convertValue(userDetails.get(i));
		}
		return List<user>;
	}
	public List<User> getUserdetails()
		{	
		return userRepository.findAll();
		}
	public Optional<User> getsingleUser(int id)
		{
		return userRepository.findById(id);
		}
	public User updateEmployee(UserVO obj)
		{
		
		}
}
