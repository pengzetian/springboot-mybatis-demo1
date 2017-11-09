package com.test.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:  批量导入数据操作进度
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */
@Data
public class ImportProgress implements Serializable{
    
    private static final long serialVersionUID = 4085456590467467078L;
    
    //导入总条数
    private Integer total;
    
    //已发送条数
    private Integer success;
    
    //发送失败条数
    private Integer failed;
    
    //剩余条数
    private Integer remain;
}
