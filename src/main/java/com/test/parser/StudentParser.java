package com.test.parser;

import com.test.model.Student;
import com.test.utils.ExcelParser;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.StringUtils;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */
public class StudentParser extends ExcelParser<Student>{
    
    private final static Integer STUDENT_ID = 0;
    private final static Integer STUDENT_NAME = 1;
    private final static Integer STUDENT_AGE  = 2;
    
    
    @Override
    protected boolean checkDataIsSame(Student student, Student currentData) {
        return false;
    }
    
    @Override
    protected void parseObj(Row row, Integer rowNum) {
        // 第一行，模板说明
        if (rowNum < 1) {
            return;
        }
        if (currentData == null) {
            currentData = new Student();
            setBaseInfo(row);
        }
    }
    
    private void setBaseInfo(Row row) {
        //把信息set 进来
        if (StringUtils.hasText(getStringValue(row.getCell(STUDENT_ID)))) {
            currentData.setId(getLongValue(row.getCell(STUDENT_ID)));
        }
        
        if (StringUtils.hasText(getStringValue(row.getCell(STUDENT_NAME)))) {
            currentData.setName(getStringValue(row.getCell(STUDENT_NAME)));
        }
        
        if (StringUtils.hasText(getStringValue(row.getCell(STUDENT_AGE)))) {
            currentData.setAge(getIntegerValue(row.getCell(STUDENT_AGE)));
        }
    }
    
    
}
