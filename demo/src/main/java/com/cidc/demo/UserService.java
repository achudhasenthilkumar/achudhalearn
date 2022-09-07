package com.cidc.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserRepository userRepository;

	public JSONObject getUser() {
		String url = "https://reqres.in/api/users";
		RestTemplate resttemplate = new RestTemplate();
		JSONObject jsonObject = resttemplate.getForObject(url, JSONObject.class);
		saveUser(jsonObject);
		return jsonObject;
	}

	public List<User> saveUser(JSONObject jsonObject) {

		Map<String, User> user = new HashMap<>();
		List<User> userDetails = (List<User>) jsonObject.get("data");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password=passwordEncoder.encode("given_password");
				for (int i = 0; i < userDetails.size(); i++) {
			User userDetail = this.objectMapper.convertValue(userDetails.get(i), User.class);
			User useremail = userRepository.findByEmail(userDetail.getEmail());
			if (useremail == null) {
				user.put(userDetail.getEmail(), userDetail);
				userDetail.setPassword(password);
			} else if (useremail != null) {
				System.out.println("Already exist");
			}
		}
		userRepository.saveAll(user.values());
		return userDetails;
	}

	public List<User> getUserdetails() {
		return userRepository.findAll();
	}

	public Optional<User> getsingleUser(int id) {
		return userRepository.findById(id);
	}

	public Optional<User> updateEmployee(UserVO obj) {
		Optional<User> users = userRepository.findById(obj.getId());

		users.get().setEmail(obj.getEmail());
		users.get().setAvatar(obj.getAvatar());
		users.get().setFirst_name(obj.getFirst_name());
		users.get().setLast_name(obj.getLast_name());
		userRepository.save(users.get());
		return users;
	}

	public String delete() {
		userRepository.deleteAll();
		return "deleted";
	}

	public Object deleteUser(int id) throws Exception {
		Optional<User> user = userRepository.findById(id);
		if (user.get() != null) {
			userRepository.deleteById(id);
		} else {
			return id + " does not exist";
		}
		return id;
	}
	public Object CreateUser(UserVO obj) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password=passwordEncoder.encode("given_password");
	    User user = new User();
	    user.setAvatar(obj.getAvatar());
	    user.setEmail(obj.getEmail());
	    user.setFirst_name(obj.getFirst_name());
	    user.setLast_name(obj.getLast_name());
	    user.setPassword(password);
	    User savedUser = userRepository.save(user);
		return savedUser;
	}
	
}
