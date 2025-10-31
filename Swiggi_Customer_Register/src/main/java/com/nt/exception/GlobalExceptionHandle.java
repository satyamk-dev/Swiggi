package com.nt.exception;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.nt.utility.Constant;
import com.nt.utility.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandle {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> GlobleExceptionHandling(Exception e, WebRequest request){
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpURLConnection.HTTP_BAD_REQUEST,
				e.getMessage(),
				request.getDescription(false)
				);
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(CustomerIdNotFoundException.class)
	public ResponseEntity<Object> HandleException(CustomerIdNotFoundException ex){
		
		List<String> list = new ArrayList<>();
		
		list.add("Error Details : CustomerIDNotFoundException ");
		list.add("ErrorMessage : "+ ex.getLocalizedMessage());
		list.add("TimeStamp : "+ System.currentTimeMillis() );
		ErrorResponse err = new ErrorResponse(HttpURLConnection.HTTP_NOT_FOUND, Constant.FAILED, list);
		return ResponseEntity.ok(err);
		
	}

}
