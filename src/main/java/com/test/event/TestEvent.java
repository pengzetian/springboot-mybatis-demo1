package com.test.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/10/31
 * Email: xionggao@terminus.io
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEvent {
    
    private Long id;
}
