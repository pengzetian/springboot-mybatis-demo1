package com.test.service;

import com.test.dao.StudentDao;
import com.test.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/10/29
 * Email: xionggao@terminus.io
 */
@Service
public class StudentService {
    
    @Autowired
    private StudentDao studentDao;
    
    
    public List<Student> list() {
        return studentDao.listAll();
    }
    
    
    public Student findById(Long id) {
        return studentDao.findById(id);
    }
    
}

