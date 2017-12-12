package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Hiki
 * @create 2017-11-06 10:34
 */
public class FileLoader {

	public static List<String[]> loadExcelFile(String fileName, int columns, int skipRows){
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream excelFile =  classLoader.getResourceAsStream(fileName);
			Workbook workbook = new HSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			// skip rows
			for (int i = 0; i < skipRows; i++)
				if (rowIterator.hasNext())
					rowIterator.next();
			// begin reading
			List<String[]> res = new ArrayList<>();
			while (rowIterator.hasNext()){
				String[] item = new String[columns];
				Row currentRow = rowIterator.next();
				for(int i = 0; i < columns; i++){
					Cell currentCell = currentRow.getCell(i);
					currentCell.setCellType(CellType.STRING);
					item[i] = currentCell.getStringCellValue();
//					System.out.print(currentCell.getStringCellValue() + "--");
				}
				res.add(item);
//				System.out.println();
			}
			return res;
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static List<String[]> loadTxtFile(String fileName, int skipRows){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream txtFile =  classLoader.getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(txtFile));
		// skip rows
		try {
			for (int i = 0; i < skipRows; i++)
				br.readLine();
		} catch (IOException e) {
			System.err.println("error while skipping rows in " + fileName + " because of: " + e);
		}
		// begin reading
		List<String[]> res = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				res.add(line.split(";"));
			}
		} catch (IOException e) {
			System.err.println("error while reading " + fileName + " because of: " + e);
		}
		return res;
	}

	public static void main(String[] args) {
		List<String[]> res = FileLoader.loadTxtFile("data/电话.txt",  1);
		for (String[] item: res) {
			for (String i: item)
				System.out.print(i + "---");
			System.out.println();
		}
	}

}
