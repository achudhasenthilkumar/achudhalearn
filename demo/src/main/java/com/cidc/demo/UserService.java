package com.cidc.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
		Map<String, User> dbUser = new HashMap<>();
		List<User> userList = (List<User>) jsonObject.get("data");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("given_password");
		for (int i = 0; i < userList.size(); i++) {
			User userDetail = this.objectMapper.convertValue(userList.get(i), User.class);
			user.put(userDetail.getEmail(), userDetail);
		}
		List<User> users = userRepository.findByEmailIn(user.keySet());
		
		for (User userdetail : users) {
			dbUser.put(userdetail.getEmail(), userdetail);
		}

		for (int i = 0; i < userList.size(); i++) {
			
			User detailsOfUser = this.objectMapper.convertValue(userList.get(i), User.class);
			String email = String.valueOf(detailsOfUser.getEmail());
			String avatar = String.valueOf(detailsOfUser.getAvatar());
			String firstName = String.valueOf(detailsOfUser.getFirst_name());
			String lastName = String.valueOf(detailsOfUser.getLast_name());
			User userDetail = dbUser.get(email);
			if (userDetail == null) {
				userDetail = new User();
				userDetail.setEmail(email);
				userDetail.setPassword(password);
			}
			userDetail.setAvatar(avatar);
			userDetail.setFirst_name(firstName);
			userDetail.setLast_name(lastName);
			dbUser.put(userDetail.getEmail(), userDetail);
		}
		userRepository.saveAll(dbUser.values());
		return userList;
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

	public Object deleteUser(int id) throws Exception{
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

		User user = new User();
		user.setAvatar(obj.getAvatar());
		user.setFirst_name(obj.getFirst_name());
		user.setLast_name(obj.getLast_name());

		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Pattern p = Pattern.compile(regex);

		if (p.matcher(obj.getPassword()).find()) {
			String password = passwordEncoder.encode(obj.getPassword());
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

	public Page<User> getAllUser(Integer pageNo, Integer pageSize, String sortBy) {
		String Desc = "Desc";
		String Asc = "Asc";
		if (sortBy.equals(Asc)) {
			Page<User> user = userRepository.findAllByOrderByIdAsc(PageRequest.of(pageNo, pageSize));
			return user;
		} else if (sortBy.equals(Desc)) {
			Page<User> userNew = userRepository.findAllByOrderByIdDesc(PageRequest.of(pageNo, pageSize));
			return userNew;
		} else {
			return null;
		}
	}
}
//User userNew = new User();
//userNew.setEmail(userDetail.getEmail());
//userNew.setAvatar(userDetail.getAvatar());
//userNew.setFirst_name(userDetail.getFirst_name());
//userNew.setLast_name(userDetail.getLast_name());
//userNew.setPassword(password);
//userRepository.save(userNew);
//Sort sortOrder = Sort.by(sortBy); 

//List<User> list = userRepository.findAll(sortOrder);

//System.out.println(list);

//PageRequest.of(pageSize,pageNo,sortBy)