package com.nt.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ICommentListService {

	public void BulkCommentData(MultipartFile file) throws IOException;
	
}
