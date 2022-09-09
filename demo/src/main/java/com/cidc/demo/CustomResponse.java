package com.cidc.demo;

import org.springframework.http.HttpStatus;

public class CustomResponse {
	
	public static ResponseVO generateResponse(String message,HttpStatus httpStatus,Object response)
	{
			ResponseVO resVO = new ResponseVO();
			resVO.setCode(httpStatus.value());
			resVO.setMessage(message);
			resVO.setResponse(response);
			return resVO;
	}	
}


//Map<String,Object> map = new HashMap<String,Object>();
//map.put("Status", message);
//map.put("code", httpStatus);
//map.put("response",response);
//return new ResponseEntity<Object>(map,httpStatus);