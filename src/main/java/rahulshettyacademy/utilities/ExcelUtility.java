package rahulshettyacademy.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelUtility {

    public static ArrayList<String> getDataFromExcel(String excelPath,String nameSheet,String columnName,String rowName) throws IOException {

        ArrayList<String> data = new ArrayList<String>();

        FileInputStream excelFileStream = new FileInputStream(System.getProperty("user.dir")+excelPath);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelFileStream);
        int numOfSheets = xssfWorkbook.getNumberOfSheets();
        for(int i = 0; i < numOfSheets; i++){

            String sheetName = xssfWorkbook.getSheetName(i);
            if(sheetName.equalsIgnoreCase(nameSheet)){

                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
                Iterator<Row> rowIterator = xssfSheet.iterator();
                Row firstRow =   rowIterator.next();
                Iterator<Cell> cellIterator = firstRow.cellIterator();

                int counter = 0;
                int columnIndex = 0;
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    if(cell.getStringCellValue().equalsIgnoreCase(columnName)){

                        // Identify  column name for Testcases text
                        columnIndex = counter;
                    }

                    counter++;
                }



                while (rowIterator.hasNext()){

                    Row eachRow =   rowIterator.next();
                    if(eachRow.getCell(columnIndex).getStringCellValue().equalsIgnoreCase(rowName)){
                        // Scan entire column to identify Purchase text
                        cellIterator = eachRow.cellIterator();
                        while (cellIterator.hasNext()){
                            // Scan entire row to get all data
                            Cell eachCell = cellIterator.next();
                            if(eachCell.getCellType() == CellType.STRING){
                                data.add(eachCell.getStringCellValue());
                            }else if(eachCell.getCellType() == CellType.NUMERIC){
                                data.add(String.valueOf(eachCell.getNumericCellValue()));
                            }else if(eachCell.getCellType() == CellType.BOOLEAN){
                                data.add(String.valueOf(eachCell.getBooleanCellValue()));
                            }else {
                                System.out.println("Invalid Cell Type");
                            }
                        }

                    }

                }


            }

        }


        return data;


    }

}
