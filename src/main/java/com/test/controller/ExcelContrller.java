package com.test.controller;


import com.test.logic.StudentLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *  这里测试下 excel 文件 导入，下载
 */
@Slf4j
@RestController
@RequestMapping("/api/student")
public class ExcelContrller {
    
    @Autowired
    private StudentLogic studentLogic;
    
    /**
     *  导入 学生的信息
     * @param file  上传的文件
     */
    @PostMapping("import")
    public void importStudentInfo(@RequestBody MultipartFile file) {
        studentLogic.importStudentInfo(file);
    }
    
    
    
    
    
}
