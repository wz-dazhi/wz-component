package com.wz.common;

import com.wz.common.util.SpringContextUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @projectName: wz-component
 * @package: com.wz.common
 * @className: AppTest
 * @description:
 * @author: zhi
 * @date: 2021/7/30
 * @version: 1.0
 */
@SpringBootTest(classes = Main.class)
public class AppTest {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void start() {
        log.info("springContextUtil: {}", SpringContextUtil.getBean(SpringContextUtil.class));
    }

}
