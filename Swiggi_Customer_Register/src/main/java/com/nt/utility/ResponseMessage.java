package com.nt.utility;

import java.util.List;

public class ResponseMessage {
	
	private Integer StatusCode;
	
	private String status;
	
	private String message;
	
	private Object object;
	
	private List<?> list;

	public ResponseMessage(Integer statusCode, String status, String message, Object object, List<?> list) {
		super();
		StatusCode = statusCode;
		this.status = status;
		this.message = message;
		this.object = object;
		this.list = list;
	}

	public ResponseMessage(Integer statusCode, String status, String message) {
		super();
		StatusCode = statusCode;
		this.status = status;
		this.message = message;
	}

	public ResponseMessage(Integer statusCode, String status, String message, Object object) {
		super();
		StatusCode = statusCode;
		this.status = status;
		this.message = message;
		this.object = object;
	}

	public ResponseMessage(Integer statusCode, String status, String message, List<?> list) {
		super();
		StatusCode = statusCode;
		this.status = status;
		this.message = message;
		this.list = list;
	}
	
	

}
