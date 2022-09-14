package com.cidc.demo;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
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

//	@Scheduled(fixedRate = 5000)
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
		String password = passwordEncoder.encode("given_password");
		for (int i = 0; i < userDetails.size(); i++) {
			User userDetail = this.objectMapper.convertValue(userDetails.get(i), User.class);
//			User userss = userRepository.findByEmail(userDetail.getEmail());
			Object users = userRepository.findByEmailIn(user.keySet());

			if (users == null) {
				user.put(userDetail.getEmail(), userDetail);
				userDetail.setPassword(password);
			} else {
				User userNew = new User();
				userNew.setEmail(userDetail.getEmail());
				userNew.setAvatar(userDetail.getAvatar());
				userNew.setFirst_name(userDetail.getFirst_name());
				userNew.setLast_name(userDetail.getLast_name());
				userNew.setPassword(password);
				userRepository.save(userNew);
			}
		}
		userRepository.saveAll(user.values());
		return userDetails;
	}

	public List<User> getUserdetails() {
		return userRepository.findAll();
	}

	public Object getsingleUser(int id) {
		Optional<User> value = userRepository.findById(id);
		if (value.isEmpty()) {
			return id + " : does not exist";
		} else {
			return userRepository.findById(id);
		}
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

	public User CreateUser(UserVO obj) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("given_password");

		User user = new User();
		user.setAvatar(obj.getAvatar());
		user.setFirst_name(obj.getFirst_name());
		user.setLast_name(obj.getLast_name());

		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Pattern p = Pattern.compile(regex);

		if (p.matcher(obj.getPassword()).find()) {
			user.setPassword(password);
		} else {
			System.out.println("please check the given password");
		}
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (pat.matcher(obj.getEmail()).find()) {
			user.setEmail(obj.getEmail());
		} else {
			System.out.println("please check the given email");
		}
		userRepository.save(user);
		return user;
	}

	public Page<User> getAllUser(Integer pageNo, Integer pageSize,String sortBy) {
		String Desc="Desc";
		String Asc="Asc";
		if(sortBy.equals(Asc))
		{
			Page<User> user = userRepository.findAllByOrderByIdAsc(PageRequest.of(pageNo, pageSize));
			return user;
		}
		else if(sortBy.equals(Desc))
		{
			Page<User> userNew = userRepository.findAllByOrderByIdDesc(PageRequest.of(pageNo, pageSize));
			return userNew;
		}
		else
		{
		return null;
		}
	}
}
//Sort sortOrder = Sort.by(sortBy); 
//
//List<User> list = userRepository.findAll(sortOrder);
//
//System.out.println(list);

//PageRequest.of(pageSize,pageNo,sortBy)