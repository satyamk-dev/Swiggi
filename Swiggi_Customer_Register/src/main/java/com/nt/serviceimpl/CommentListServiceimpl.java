package com.nt.serviceimpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nt.entity.Comment;
import com.nt.repository.UserCommentRepository;
import com.nt.service.ICommentListService;
import com.nt.utility.Helper;



@Service
public class CommentListServiceimpl implements ICommentListService {
	@Autowired
	private UserCommentRepository ucr;

	@Override
	public void BulkCommentData(MultipartFile file) throws IOException {
		
			List<Comment> excelDataStoreDataBase = Helper.excelDataStoreDataBase(file.getInputStream());
			
			 ucr.saveAll(excelDataStoreDataBase);
		
		 
	}

}
