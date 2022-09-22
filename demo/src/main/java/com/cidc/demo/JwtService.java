package com.cidc.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtil jwtUtil;


public ResponseVO Jwt(UserVO obj) {

		User users=userRepository.findByEmail(obj.getEmail());

		String decodepass=users.getPassword();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if(users==null)
		{
		return CustomResponse.generateResponse("no value entered",HttpStatus.BAD_REQUEST,users);
		}
		else if(passwordEncoder.matches(obj.getPassword(),decodepass))
		{
		String token=jwtUtil.generateJWT(users);
        Map<String , Object> data = new HashMap<>();
        data.put("accessToken", token);
        return CustomResponse.generateResponse("your token",HttpStatus.ACCEPTED, data);
		}
		return CustomResponse.generateResponse("check email and password",HttpStatus.BAD_REQUEST,"please check your email and password");
	}
}