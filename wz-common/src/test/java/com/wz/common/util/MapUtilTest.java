package com.wz.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MapUtilTest {

    @Test
    public void beanToMap() {
        Bean b = new Bean("zhangsan", 18, BigDecimal.TEN, new Date(), LocalDateTime.now(), true, 1.1D, 1.2D, 2L, 2L, 3, 3, Bean.T.T2);
        Map<String, Object> map = MapUtil.beanToMap(b);
        System.out.println("Bean to Map: " + map);
        System.out.println("Map to Bean: " + MapUtil.mapToBean(map, new Bean()));
    }

    @Test
    public void mapToBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        map.put("name", "zhangsan");
        map.put("age", 18);
        map.put("price", BigDecimal.TEN);
        map.put("date", new Date());
        map.put("birth", LocalDateTime.now());
        map.put("b", true);
        map.put("d1", 1.1);
        map.put("d2", 1.1);
        map.put("l1", 2);
        map.put("l2", 2);
        map.put("i1", 3);
        map.put("i2", 3);
        map.put("t", Bean.T.T1);

        log.info("Map to Bean: {}", MapUtil.mapToBean(map, new Bean()));
    }

    @Test
    public void objectsToMaps() {
        Bean b1 = new Bean("zhangsan", 18, BigDecimal.ONE, new Date(), LocalDateTime.now(), false, 1.1D, 1.2D, 2L, 2L, 3, 3, Bean.T.T1);
        Bean b2 = new Bean("lisi", 20, BigDecimal.TEN, new Date(), LocalDateTime.now(), true, 1.1D, 1.2D, 2L, 2L, 3, 3, Bean.T.T2);
        List<Bean> beans = new ArrayList<>();
        beans.add(b1);
        beans.add(b2);

        log.info("Objects to Maps: {}", MapUtil.objectsToMaps(beans));
    }

    @Test
    public void mapsToObjects() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "zhangsan");
        map1.put("age", 18);
        map1.put("price", BigDecimal.ONE);
        map1.put("date", new Date());
        map1.put("birth", LocalDateTime.now());
        map1.put("b", false);
        map1.put("d1", 1.1);
        map1.put("d2", 1.1);
        map1.put("l1", 2);
        map1.put("l2", 2);
        map1.put("i1", 3);
        map1.put("i2", 3);
        map1.put("t", Bean.T.T1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "lisi");
        map2.put("age", 20);
        map2.put("price", BigDecimal.TEN);
        map2.put("date", new Date());
        map2.put("birth", LocalDateTime.now());
        map2.put("b", true);
        map2.put("d1", 1.1);
        map2.put("d2", 1.1);
        map2.put("l1", 2);
        map2.put("l2", 2);
        map2.put("i1", 3);
        map2.put("i2", 3);
        map2.put("t", Bean.T.T2);

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);

        log.info("Maps to Objects: {}", MapUtil.mapsToObjects(list, Bean.class));
    }

    @Test
    public void isEmpty() {
        Assertions.assertTrue(MapUtil.isEmpty(null));
        Assertions.assertTrue(MapUtil.isEmpty(new HashMap<>()));
    }

    @Test
    public void isNotEmpty() {
        Assertions.assertTrue(MapUtil.isNotEmpty(new HashMap() {{
            put("k", "v");
        }}));
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Bean implements Comparable<Bean> {
    private String name;
    private int age;
    private BigDecimal price;
    private Date date;
    private LocalDateTime birth;
    private boolean b;
    private Double d1;
    private double d2;
    private Long l1;
    private long l2;
    private Integer i1;
    private int i2;
    private T t;

    @Override
    public int compareTo(Bean b) {
        return this.price.compareTo(b.price);
    }

    enum T {
        T1, T2
    }
}