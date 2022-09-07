package com.cidc.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class CustomResponse {
	
	public static ResponseEntity<Object> generateResponse(String message,HttpStatus httpStatus,Object response)
	{
			
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("Status", message);
	    	map.put("code", httpStatus);
	    	map.put("response",response);
			return new ResponseEntity<Object>(map,httpStatus);
	}	
}