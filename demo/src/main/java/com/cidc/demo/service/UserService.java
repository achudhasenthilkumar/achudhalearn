package com.cidc.demo.service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cidc.demo.entity.ResponseVO;
import com.cidc.demo.entity.User;
import com.cidc.demo.entity.UserVO;
import com.cidc.demo.repository.UserRepository;
import com.cidc.demo.response.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/* Service class of the usercontroller
 */
@Service
public class UserService extends CustomResponse {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserRepository userRepository;

	/* Calls the third party api and get all the details of the users
	 * Converted the userdetails into jsonobject and pass into saveUser
	 */
//	@Scheduled(fixedRate = 5000)
	public JSONObject getUser() {
		String url = "https://reqres.in/api/users";
		RestTemplate resttemplate = new RestTemplate();
		JSONObject jsonObject = resttemplate.getForObject(url, JSONObject.class);
		saveUser(jsonObject);
		return jsonObject;
	}

	/* From the jsonobject we get the userdetails
	 * Store the details of the users into the database by using map
	 */
	public ResponseVO saveUser(JSONObject jsonObject) {

		Map<String, User> user = new HashMap<>();
		Map<String, User> dbUser = new HashMap<>();
		List<User> userList = (List<User>) jsonObject.get("data");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("User@01");
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
	    return super.generateResponse("Sucess", "verified",HttpStatus.OK,userList);
	}

	/* Get all the details of the users from database
	 */
	public ResponseVO getUserdetails() {
		 Object user=userRepository.findAll();
		 return super.generateResponse("Sucess", "verified",HttpStatus.OK,user);
	}

	/* Get the Single user details by id
	 * If the user is not available it shows id does not exist
	 */
	public ResponseVO getsingleUser(int id, HttpServletResponse response) throws IOException{
		Optional<User> value = userRepository.findById(id);
		System.out.println(userRepository.findById(id));
		if (value.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return super.generateResponse("failed", "Check your id",HttpStatus.NOT_FOUND,"error");
		}
		else {
			response.setStatus(HttpServletResponse.SC_OK);
			return super.generateResponse("Sucess", "verified",HttpStatus.OK,userRepository.findById(id));
		}
	}
	/* Update the details of the users
	 * By using id
	 */
	public ResponseVO updateEmployee(UserVO obj) {
		Optional<User> users = userRepository.findById(obj.getId());
		if(users.isPresent())
		{
		users.get().setEmail(obj.getEmail());
		users.get().setAvatar(obj.getAvatar());
		users.get().setFirst_name(obj.getFirst_name());
		users.get().setLast_name(obj.getLast_name());
		userRepository.save(users.get());
		System.out.println("users"+users);
		return super.generateResponse("Sucess", "verified",HttpStatus.OK,users);
		}
		else
		{
		return super.generateResponse("failed", "Check your id",HttpStatus.NOT_FOUND,null);
		}
	}

	/* Delete all the userdetails
	 */
	public ResponseVO delete() {
		userRepository.deleteAll();
		return super.generateResponse("Sucess", "verified",HttpStatus.OK,null);
	}

	/* Delete a individual userdetail
	 * By using a id
	 */
	public ResponseVO deleteUser(int id) throws Exception {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.deleteById(id);
			return super.generateResponse("Sucess", "verified",HttpStatus.OK,id+" deleted");
		}
		else if(user.isEmpty()) {
			return super.generateResponse("failed", "Check your id",HttpStatus.NOT_FOUND,null);
		}
		return null;
	}

	/* Create a new user
	 * And inserted the new user in database
	 * The email and passwords will be validate by using regular expression
	 */
	public ResponseVO CreateUser(UserVO obj) {
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
		return super.generateResponse("Sucess", "verified",HttpStatus.OK,user);
	}

	/* Get all the users using pagination
	 * We will have sortby in this
	 */
	public ResponseVO getAllUser(Integer pageNo, Integer pageSize, String sortBy) {
		String Desc = "Desc";
		String Asc = "Asc";
		if (sortBy.equals(Asc)) {
			Page<User> user = userRepository.findAllByOrderByIdAsc(PageRequest.of(pageNo, pageSize));
			return super.generateResponse("Sucess", "verified",HttpStatus.OK,user);
		} else if (sortBy.equals(Desc)) {
			Page<User> userNew = userRepository.findAllByOrderByIdDesc(PageRequest.of(pageNo, pageSize));
			return super.generateResponse("Sucess", "verified",HttpStatus.OK,userNew);
		} else {
			return null;
		}
	}

	/* Get the userdetails from token which we are generated
	 * Splited the given tokens
	 * Decoded with base64 and using objectmapper get the email
	 * By using email get the details of the given user
	 */
	public ResponseVO getAuth(String Authorization) throws JsonMappingException, JsonProcessingException {
		String[] chunks = Authorization.split("\\.");

		Base64.Decoder decoder = Base64.getUrlDecoder();

		String header = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));
		String Signature = new String(decoder.decode(chunks[2]));
		ObjectMapper mapper = new ObjectMapper();
		JsonNode js = mapper.readTree(payload);
		String ab = js.findValue("sub").asText();
		User usernew = userRepository.findByEmail(ab);
		return super.generateResponse("Sucess", "verified",HttpStatus.OK,usernew);
	}
}