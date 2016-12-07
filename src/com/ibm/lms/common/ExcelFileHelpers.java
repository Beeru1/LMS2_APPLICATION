package com.ibm.lms.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileHelpers {
	
	
	public static Sheet readExcelFile(File file , String path) {
		try {
			InputStream fileInputStream  = new FileInputStream(file);
			String fileName =  file.getName();
			Sheet sheet  = null;
			if(fileName.substring(fileName.lastIndexOf(".")+1).equalsIgnoreCase("xls")) {
				 POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
				HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
				  sheet = workbook.getSheetAt(0);
				return sheet;
			}else if(fileName.substring(fileName.lastIndexOf(".")+1).equalsIgnoreCase("xlsx")) {
				XSSFWorkbook workbook = new XSSFWorkbook(path);
				 sheet = workbook.getSheetAt(0);
			    return sheet;
			}else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
