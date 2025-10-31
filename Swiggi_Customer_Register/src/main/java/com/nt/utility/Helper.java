package com.nt.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.nt.entity.Comment;

public class Helper {

	public static boolean CheckFileExcel(MultipartFile file) {
		return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}
	
	public static List<Comment> excelDataStoreDataBase(InputStream inputStream) throws IOException{
	
		List<Comment> list = new ArrayList<>();
		
		Set<String> unique = new HashSet<>();
		
		XSSFWorkbook xfw = new XSSFWorkbook(inputStream);
		
		XSSFSheet sheetAt = xfw.getSheetAt(0);
		
		Iterator<Row> iteratorRow = sheetAt.iterator();
		
		if(iteratorRow.hasNext()) {
			iteratorRow.next();
		}
		
		while(iteratorRow.hasNext()) {
			Row next = iteratorRow.next();
			
			Iterator<Cell> iteratorCells = next.iterator();
			
			Comment com = new Comment();
			
			while(iteratorCells.hasNext()) {
				
				Cell cell  = iteratorCells.next();
				
				switch (cell.getColumnIndex()) {
				case 0: {
					  com.setCustomerName(cell.getStringCellValue());
					  break;
				}
				case 1:{
					com.setComment(cell.getStringCellValue());
				}
				
				case 2:{
					com.setStars(cell.getNumericCellValue());
				}
				default:
					break;
				}
				
				if(unique.add(com.getCustomerName())) {
					list.add(com);
				}
				
			}
			
			
		}
		
		return list;
	}
	
	
}
