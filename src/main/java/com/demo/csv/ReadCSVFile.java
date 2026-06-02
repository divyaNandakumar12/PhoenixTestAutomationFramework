package com.demo.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) throws IOException, CsvException {
		
		InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");
		InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
		
		CSVReader csvReader=new CSVReader(inputStreamReader); 
		List<String[]> dataList=csvReader.readAll();
		
		for(String[] dataStrings:dataList) {
			for(String data:dataStrings) {
				System.out.print(data+" ");
			}
			System.out.println();
		}
	}

}
