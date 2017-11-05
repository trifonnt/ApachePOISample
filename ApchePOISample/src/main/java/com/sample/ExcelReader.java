package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static void main(String[] args) {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try {
			String excelFilePath = "src/main/java/com/sample/Orders.xlsx";
			inputStream = new FileInputStream(new File(excelFilePath));
			System.out.println(excelFilePath);
			workbook = getRelevantWorkbook(inputStream, excelFilePath);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellTypeEnum()) {
					case STRING:
						System.out.print(cell.getStringCellValue());
						break;
					case NUMERIC:
						System.out.print(cell.getNumericCellValue());
						break;
					case BOOLEAN:
						System.out.print(cell.getBooleanCellValue());
						break;
					default:
						break;
					}
					System.out.print("; ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {workbook.close();} catch (IOException e) {/* ignored */}
			}
			if (inputStream != null) {
				try {inputStream.close();} catch (IOException e) {/* ignored */}
			}
		}
	}

	private static Workbook getRelevantWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("Incorrect file format");
		}
		return workbook;
	}

}
