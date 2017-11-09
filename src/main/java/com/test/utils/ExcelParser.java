package com.test.utils;


import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.test.exception.JsonResponseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * excel 解析抽象类
 *
 */
@Slf4j
public abstract class ExcelParser<T> {
    
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    
    protected T currentData = null;
    
    public List<T> parseToObj(MultipartFile file) throws IllegalAccessException, InstantiationException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<T> list = Lists.newArrayList();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                // 解析工作表
                parseSheet(sheet, list);
            }
            
            // 最后一条数据是否已经插入了，如果没有，则插入最后一条数据
            if (checkLastRecordNotInsert(list)) {
                putData(list);
            }
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("close workbook fail, case {}", Throwables.getStackTraceAsString(e));
                throw new JsonResponseException("excel.parse.fail");
            }
        }
        return list;
    }
    
    private void parseSheet(Sheet sheet, List<T> list) {
        //获得当前sheet的开始行
        int firstRowNum = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        //循环除了内容的所有行
        for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
            //获得当前行
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            // 判断是否需要插入数据
            if (isNextData(row)) {
                putData(list);
            }
            
            // 获取数据
            parseObj(row, rowNum);
        }
    }
    
    /**
     * 判断最后一条数据是否插入到集合中
     *
     * @param list 结果集
     * @return 最后一条数据是否插入到集合中
     */
    private boolean checkLastRecordNotInsert(List<T> list) {
        if (list.isEmpty()) {
            return true;
        }
        if (!checkDataIsSame(list.get(list.size() - 1), currentData)) {
            return true;
        }
        return false;
    }
    
    protected abstract boolean checkDataIsSame(T t, T currentData);
    
    private void putData(List<T> list) {
        if (currentData == null) {
            return;
        }
        list.add(currentData);
        currentData = null;
    }
    
    protected boolean isNextData(Row row) {
        return true;
    }
    
    private void checkFile(MultipartFile file) {
        //判断文件是否存在
        if (null == file) {
            log.error("file not found");
            throw new JsonResponseException("file.not.exist");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
            log.error("{} is not a excel file!", fileName);
            throw new JsonResponseException("file.type.mismatch");
        }
    }
    
    private Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(xls)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(xlsx)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            log.error("get workbook fail, case {}", Throwables.getStackTraceAsString(e));
        }
        return workbook;
    }
    
    protected abstract void parseObj(Row row, Integer rowNum);
    
    protected String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
    
    protected Double getDoubleValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return Double.valueOf(cell.getStringCellValue());
    }
    
    protected Integer getIntegerValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return Integer.valueOf(cell.getStringCellValue());
    }
    
    protected Long getLongValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return Long.valueOf(cell.getStringCellValue());
    }
}
