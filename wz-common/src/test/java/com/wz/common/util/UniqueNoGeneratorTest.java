package com.wz.common.util;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class UniqueNoGeneratorTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
//        UniqueNoGenerator idWorker = new UniqueNoGenerator(0, 0);
        UniqueNoGenerator idWorker = new UniqueNoGenerator();

//        Set<Long> idSet = new HashSet<>();
        Set<String> idSet = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
//            long nextId = idWorker.nextId();
//            idSet.add(nextId);
            String s = idWorker.nextIdStr();
            idSet.add(s);
//            System.out.println(nextId);
//            System.out.println(s);
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(idSet.size());
    }

}