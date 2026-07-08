package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;
import com.google.common.collect.Table.Cell;

public class ExcelReader2 {

	public static Iterator<UserCredentials> loadTestData(){
		
		
		InputStream isInputStream= Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkbook = null;
		try {
			myWorkbook = new XSSFWorkbook(isInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet mySheet= myWorkbook.getSheet("LoginTestData");
		
		
		XSSFRow rowHeader= mySheet.getRow(0);
		int usernameIndex=-1;
		int passwordIndex=-1;
		for(org.apache.poi.ss.usermodel.Cell cell:rowHeader) {
			if(cell.getStringCellValue().trim().equalsIgnoreCase("username")){
				usernameIndex=cell.getColumnIndex();
			}
			if(cell.getStringCellValue().trim().equalsIgnoreCase("password")){
				passwordIndex=cell.getColumnIndex();
			}
			
		}
		
		System.out.println(usernameIndex);
		System.out.println(passwordIndex);
		
		int lastRowIndex=mySheet.getLastRowNum();
		UserCredentials userCredentials;
		List<UserCredentials> userList=new ArrayList<UserCredentials>();
		for(int rowIndex=1;rowIndex<=lastRowIndex;rowIndex++) {
			XSSFRow rowdata= mySheet.getRow(rowIndex);
			userCredentials=new UserCredentials(rowdata.getCell(usernameIndex).toString(), rowdata.getCell(passwordIndex).toString());
			System.out.println(userCredentials);
			userList.add(userCredentials);
		}
		
		return userList.iterator();
	}

}
