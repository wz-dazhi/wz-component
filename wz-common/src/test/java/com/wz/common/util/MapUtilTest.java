package com.wz.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class MapUtilTest {

    @Test
    public void beanToMap() {
        Bean b = new Bean("zhangsan", 18, BigDecimal.TEN, new Date(), LocalDateTime.now(), true);
        Map<String, Object> map = MapUtil.beanToMap(b);
        log.info("Bean to Map: {}", map);
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

        log.info("Map to Bean: {}", MapUtil.mapToBean(map, new Bean()));
    }

    @Test
    public void objectsToMaps() {
        Bean b1 = new Bean("zhangsan", 18, BigDecimal.ONE, new Date(), LocalDateTime.now(), false);
        Bean b2 = new Bean("lisi", 20, BigDecimal.TEN, new Date(), LocalDateTime.now(), true);
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

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "lisi");
        map2.put("age", 20);
        map2.put("price", BigDecimal.TEN);
        map2.put("date", new Date());
        map2.put("birth", LocalDateTime.now());
        map2.put("b", true);

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);

        log.info("Maps to Objects: {}", MapUtil.mapsToObjects(list, Bean.class));
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(MapUtil.isEmpty(null));
        Assert.assertTrue(MapUtil.isEmpty(new HashMap<>()));
    }

    @Test
    public void isNotEmpty() {
        Assert.assertTrue(MapUtil.isNotEmpty(new HashMap() {{
            put("k", "v");
        }}));
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Bean {
    private String name;
    private int age;
    private BigDecimal price;
    private Date date;
    private LocalDateTime birth;
    private boolean b;
}