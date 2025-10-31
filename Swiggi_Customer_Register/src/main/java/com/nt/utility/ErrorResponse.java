package com.nt.utility;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
	
	private LocalDateTime datetime;
	
	private Integer statusCode;
	
	private String message;
	
	private String description;
	
	private List<?> list;
	
	



	public ErrorResponse(Integer statusCode, String message, List<?> list) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.list = list;
	}





	public ErrorResponse( LocalDateTime datetime,Integer statusCode, String message, String description) {
		super();
		this.datetime= datetime;
		this.statusCode = statusCode;
		this.message = message;
		this.description = description;
	}
	
	
	
}
