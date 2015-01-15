package me.arthinking.excel.parser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.arthinking.excel.item.Item;
import me.arthinking.excel.persistent.MySqlPersistentService;
import me.arthinking.excel.persistent.PersistentService;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractExcelParser {

    protected static Logger logger = Logger.getLogger("access");
    
    PersistentService persistentService = new MySqlPersistentService();
    
    public void setPersistentService(PersistentService persistentService){
        this.persistentService = persistentService;
    }
    
    /**
     * 解析一行的数据到Item对象里面，该方法由具体的解析对象根据Excel格式实现
     * @param row
     * @return
     * Item
     * @author Jason Peng
     * @update date 2015年1月14日
     */
    abstract Item parseItem(HSSFRow row);
    
    /**
     * 解析Excel文件
     * @param hssfWorkbook
     * void
     * @author Jason Peng
     * @throws SQLException 
     * @throws IOException 
     * @update date 2015年1月14日
     */
    public void parseFile(HSSFWorkbook workbook) throws SQLException{
        int sheetNum = workbook.getNumberOfSheets();
        logger.info("sheetNum: " + sheetNum);
        HSSFRow row = null;
        for(int k=0; k<sheetNum; k++){
            HSSFSheet sheet = workbook.getSheetAt(k);
            int totalSize = sheet.getLastRowNum();
            logger.info("current process sheet: " + sheetNum);
            if(totalSize <= 0){
                continue;
            }
            List<Item> itemList = new ArrayList<Item>();
            for(int i = 1; i < totalSize; i++){
                row = sheet.getRow(i);
                if(row != null){
                    Item item = parseItem(row);
                    if(itemList.size() <= 1000){
                        itemList.add(item);
                    } else {
                        persistentService.batchCombineSave(itemList);
                        itemList.clear();
                    }
                }
            }
        }
    }
    
    /*
    public void parseFile(HSSFWorkbook workbook) throws SQLException, IOException{
        int sheetNum = workbook.getNumberOfSheets();
        logger.info("sheetNum: " + sheetNum);
        HSSFRow row = null;
        FileOutputStream out = null;
        out = new FileOutputStream(new File("D:/test_script.sql"));
        for(int k=0; k<2000; k++){
            HSSFSheet sheet = workbook.getSheetAt(k);
            int totalSize = sheet.getLastRowNum();
            logger.info("current process sheet: " + sheetNum);
            if(totalSize <= 0){
                continue;
            }
            List<Item> itemList = new ArrayList<Item>();
            for(int i = 1; i < totalSize; i++){
                row = sheet.getRow(i);
                if(row != null){
                    Item item = parseItem(row);
                    // 写文件到目标文件
                    out.write("".getBytes());
                }
            }
        }
    }
    */
    
}