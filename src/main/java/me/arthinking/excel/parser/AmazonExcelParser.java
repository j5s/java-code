package me.arthinking.excel.parser;

import me.arthinking.excel.item.AmazonItem;
import me.arthinking.excel.item.Item;
import me.arthinking.excel.persistent.PersistentService;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class AmazonExcelParser extends AbstractExcelParser{

    protected static Logger logger = Logger.getLogger("access");
    
    @Override
    Item parseItem(HSSFRow row) {
        // ASIN
        HSSFCell cell = row.getCell(0);
        String asin = cell.getStringCellValue();
        // Binding
        cell = row.getCell(1);
        String binding = cell.getStringCellValue();
        // Category
        cell = row.getCell(2);
        int category = (int)cell.getNumericCellValue();
        // title
        cell = row.getCell(9);
        String title = cell.getStringCellValue();
        
        AmazonItem item = new AmazonItem();
        item.setAsin(asin);
        item.setBinding(binding);
        item.setCategory(category);
        item.setTitle(title);
        logger.info(title);
        return item;
    }
}