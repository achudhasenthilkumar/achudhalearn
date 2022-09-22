package com.cidc.demo.response;


import org.springframework.http.HttpStatus;

import com.cidc.demo.entity.ResponseVO;

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