package com.wz.common.util;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtilTest {

    @Test
    public void getMapper() {
        System.out.println(JsonUtil.getMapper());
    }

    @Test
    public void toJsonString() {
        Bean b = new Bean("zhangsan", 18, BigDecimal.ONE, new Date(), LocalDateTime.now(), false, 1.1D, 1.2D, 2L, 2L, 3, 3, Bean.T.T1);
        System.out.println(JsonUtil.toJson(b));
    }

    @Test
    public void toJsonString1() {
        Bean b = new Bean("zhangsan", 18, BigDecimal.ONE, new Date(), LocalDateTime.now(), false, 1.1D, 1.2D, 2L, 2L, 3, 3, Bean.T.T1);
        System.out.println(JsonUtil.toJson(true, b));
    }

    @Test
    public void toBean() {
        String json = "{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:28:45\",\"birth\":\"2020-05-06 12:28:45\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"}";
        System.out.println(JsonUtil.toBean(json, Bean.class));
    }

    @SneakyThrows
    @Test
    public void toBean1() {
        String filePath = "/Users/wangzhi/work/wz-component/wz-common/src/test/java/com/wz/common/util/json.json";
        System.out.println(JsonUtil.toBean(new File(filePath), Bean.class));

        System.out.println(JsonUtil.toBean(new FileInputStream(filePath), Bean.class));
        System.out.println(JsonUtil.toBean(new URL("file:" + filePath), Bean.class));
    }

    @Test
    public void toList() {
        String json = "[{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"},{\"name\":\"李四\",\"age\":20,\"price\":10,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T2\"}]";
        System.out.println(JsonUtil.toList(json, List.class, Bean.class));
        System.out.println(JsonUtil.toList(json, List.class, Bean.class).getClass());
    }

    @Test
    public void toArrayList() {
        String json = "[{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"},{\"name\":\"李四\",\"age\":20,\"price\":10,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T2\"}]";
        System.out.println(JsonUtil.toArrayList(json, Bean.class));
        System.out.println(JsonUtil.toArrayList(json, Bean.class).getClass());
    }

    @Test
    public void toLinkedList() {
        String json = "[{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"},{\"name\":\"李四\",\"age\":20,\"price\":10,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T2\"}]";
        List<Bean> linkedList = JsonUtil.toLinkedList(json, Bean.class);
        System.out.println(linkedList);
        System.out.println(linkedList.getClass());
    }

    @Test
    public void toSet() {
        String json = "[{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"},{\"name\":\"李四\",\"age\":20,\"price\":10,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T2\"}]";
        System.out.println(JsonUtil.toSet(json, Set.class, Bean.class));
        System.out.println(JsonUtil.toSet(json, Set.class, Bean.class).getClass());
    }

    @Test
    public void toHashSet() {
        String json = "[{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"},{\"name\":\"李四\",\"age\":20,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T2\"}]";
        System.out.println(JsonUtil.toHashSet(json, Bean.class));
        System.out.println(JsonUtil.toHashSet(json, Bean.class).getClass());
    }

    @Test
    public void toLinkedHashSet() {
        String json = "[{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"},{\"name\":\"李四\",\"age\":20,\"price\":10,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T2\"}]";
        System.out.println(JsonUtil.toLinkedHashSet(json, Bean.class));
        System.out.println(JsonUtil.toLinkedHashSet(json, Bean.class).getClass());
    }

    @Test
    public void toTreeSet() {
        String json = "[{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"},{\"name\":\"李四\",\"age\":20,\"price\":10,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T2\"}]";
        System.out.println(JsonUtil.toTreeSet(json, Bean.class));
        System.out.println(JsonUtil.toTreeSet(json, Bean.class).getClass());
    }

    @Test
    public void toMap() {
        String json = "{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"}";
        System.out.println(JsonUtil.toMap(json, Map.class, String.class, String.class));
        System.out.println(JsonUtil.toMap(json, Map.class, String.class, String.class).getClass());
    }

    @Test
    public void toHashMap() {
        String json = "{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"}";
        System.out.println(JsonUtil.toHashMap(json, String.class, String.class));
        System.out.println(JsonUtil.toHashMap(json, String.class, String.class).getClass());
    }

    @Test
    public void toLinkedHashMap() {
        String json = "{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"}";
        System.out.println(JsonUtil.toLinkedHashMap(json, String.class, String.class));
        System.out.println(JsonUtil.toLinkedHashMap(json, String.class, String.class).getClass());
    }

    @Test
    public void toTreeMap() {
        String json = "{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"}";
        System.out.println(JsonUtil.toTreeMap(json, String.class, String.class));
        System.out.println(JsonUtil.toTreeMap(json, String.class, String.class).getClass());
    }

    @Test
    public void toConcurrentHashMap() {
        String json = "{\"name\":\"zhangsan\",\"age\":18,\"price\":1,\"date\":\"2020-05-06 12:44:54\",\"birth\":\"2020-05-06 12:44:55\",\"b\":false,\"d1\":1.1,\"d2\":1.2,\"l1\":2,\"l2\":2,\"i1\":3,\"i2\":3,\"t\":\"T1\"}";
        System.out.println(JsonUtil.toConcurrentHashMap(json, String.class, String.class));
        System.out.println(JsonUtil.toConcurrentHashMap(json, String.class, String.class).getClass());
    }
}