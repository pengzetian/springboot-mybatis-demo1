package com.test.event;

import com.test.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUploadEvent implements Serializable{
    
    
    private static final long serialVersionUID = 3211955887497238323L;
    
    private String taskId;
    
    private List<Student> students;
}
