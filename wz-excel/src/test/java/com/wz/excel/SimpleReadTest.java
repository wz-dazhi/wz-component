package com.wz.excel;

import com.wz.excel.model.ReadSheetWrapper;
import com.wz.excel.read.handler.DefaultReadHandler;
import com.wz.excel.read.handler.ValidatorReadHandler;
import com.wz.excel.read.service.ValidatorReadService;
import com.wz.excel.util.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel
 * @className: SimpleReadTest
 * @description:
 * @author: zhi
 * @date: 2021/11/16
 * @version: 1.0
 */
public class SimpleReadTest {
    private InputStream is = SimpleReadTest.class.getClassLoader().getResourceAsStream("template/simple.xls");

    @Test
    public void testSimpleRead() {
//        List<Simple> list = EasyExcel.read(is, Simple.class, null).doReadAllSync();
        // 读所有sheet
        List<Simple> list = ExcelUtil.doReadAllSync(is, Simple.class);
        list.forEach(System.out::println);
    }

    @Test
    public void testSimpleReadListener() {
        // 只读sheet0
        ExcelUtil.doRead(is, Simple.class, new DefaultReadHandler<>(System.out::println));
    }

    @Test
    public void testSimpleValidatorReadListener() {
        ValidatorReadHandler<Simple> readHandler = new ValidatorReadHandler<>(cachedList -> System.out.println("--> " + cachedList));
        ExcelUtil.doReadSheetNo(is, 0, Simple.class, readHandler);
        // 再加载一次, 流会自动关闭
        is = SimpleReadTest.class.getClassLoader().getResourceAsStream("template/simple.xls");
        ExcelUtil.doReadSheetNo(is, 1, Simple.class, readHandler);
        readHandler.getValidateResults().forEach(System.out::println);
    }

    @Test
    public void testSimpleValidatorMultiReadListener() {
        ValidatorReadService<Simple> validatorReadService = new ValidatorReadService<>() {
            @Override
            public boolean skipInvalid() {
                return false;
            }

            @Override
            public void process(List<Simple> cachedList) {
                System.out.println("--> " + cachedList);
            }
        };
        ValidatorReadHandler<Simple> readHandler = new ValidatorReadHandler<>(validatorReadService);
        ValidatorReadHandler<Simple2> readHandler2 = new ValidatorReadHandler<>(System.out::println);

        ReadSheetWrapper<?>[] sheetWrappers = {
                new ReadSheetWrapper<>(0, Simple.class, readHandler),
                new ReadSheetWrapper<>(1, Simple.class, readHandler),
                new ReadSheetWrapper<>(2, Simple2.class, readHandler2),
        };

        ExcelUtil.doReadMultiSheetNo(is, sheetWrappers);

        readHandler.getValidateResults().forEach(System.out::println);
        System.out.println("=============Sheet2===========");
        readHandler2.getValidateResults().forEach(System.out::println);
    }

    @Test
    public void testSimpleValidatorMultiReadListener2() {
        // 一个监听读取多个sheet
        ValidatorReadService<?> validatorReadService = new ValidatorReadService<>() {
            @Override
            public boolean skipInvalid() {
                return true;
            }

            @Override
            public void process(List<Object> cachedList) {
                Class<?> clazz = cachedList.get(0).getClass();
                if (clazz == Simple.class) {
                    processSimple((List<Simple>) (Object) cachedList);
                } else if (clazz == Simple2.class) {
                    processSimple2((List<Simple2>) (Object) cachedList);
                }
            }

            private void processSimple(List<Simple> simples) {
                System.out.println("处理Simple ==> " + simples);
            }

            private void processSimple2(List<Simple2> simples) {
                System.out.println("处理Simple2 --==> " + simples);
            }
        };
        ValidatorReadHandler<?> readHandler = new ValidatorReadHandler<>(4, validatorReadService);
        ReadSheetWrapper<?>[] sheetWrappers = {
                new ReadSheetWrapper(0, Simple.class, readHandler),
                new ReadSheetWrapper(1, Simple.class, readHandler),
                new ReadSheetWrapper(2, Simple2.class, readHandler),
        };

        ExcelUtil.doReadMultiSheetNo(is, sheetWrappers);
        readHandler.getValidateResults().forEach(System.out::println);
    }
}
