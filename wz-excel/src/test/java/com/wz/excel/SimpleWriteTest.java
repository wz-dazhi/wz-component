package com.wz.excel;

import com.wz.excel.util.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel
 * @className: SimpleWriteTest
 * @description:
 * @author: zhi
 * @date: 2021/12/7
 * @version: 1.0
 */
public class SimpleWriteTest {

    @Test
    public void testEnumWrite() {
        String fileName = "自己目录/template/simple3Enum-test-write.xls";
        List<Simple3Enum> list = List.of(
                new Simple3Enum("张三", 20, 1, true),
                new Simple3Enum("李四", 18, 2, false),
                new Simple3Enum("王五", 28, 2, true)
        );
        ExcelUtil.doWrite(fileName, "测试", Simple3Enum.class, list);
    }

}
