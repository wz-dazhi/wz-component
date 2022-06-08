package com.wz.common.mapstruct;

import cn.hutool.core.lang.UUID;
import com.wz.common.Main;
import com.wz.common.mapstruct.uses.SimpleDateMapper;
import com.wz.common.util.MapUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = Main.class)
class BaseMapperTest {

    private User user;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SimpleDateMapper simpleDateMapper;

    @BeforeEach
    void userBefore() {
        //userMapper = UserMapper.USER_MAPPER;
        user = new User();
        user.setUid(UUID.fastUUID().toString());
        user.setUsername("da zhi");
        user.setPassword("123456");
        user.setAge(18);
        User.Address address = new User.Address();
        address.setProv("北京市");
        address.setCity("北京市");
        address.setCoty("朝阳区");
        user.setAddress(address);
        user.setFlag(true);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(new Date());
        user.setPrice(new BigDecimal("10.10"));
    }

    @Test
    void mapToSource() {
        Map<String, String> map = MapUtil.beanToMapStr(user);
        User user = userMapper.mapToSource(map);
        System.out.println(user);
        System.out.println(user.getAddress().getProv());
    }

    @Test
    void mapToTarget() {
        Map<String, String> map = MapUtil.beanToMapStr(user);
        UserDTO dto = userMapper.mapToTarget(map);
        System.out.println(dto);
    }

    @Test
    void sourceToTarget() {
        UserDTO dto = userMapper.sourceToTarget(user);
        System.out.println(dto);
    }

    @Test
    void targetToSource() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable command = () -> {
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            user.setUpdateTime(new Date());
            UserDTO dto = userMapper.sourceToTarget(user);
            System.out.println(Thread.currentThread().getName() + "--> " + dto);
            User u = userMapper.targetToSource(dto);
            System.out.println(Thread.currentThread().getName() + "--> " + u);
            System.out.println(Thread.currentThread().getName() + "-->----------------");
            // 清除内存
            //simpleDateMapper.remove();
        };
        service.execute(command);
        service.execute(command);
        service.execute(command);
        service.execute(command);
        service.execute(command);
        service.execute(command);
        service.execute(command);
        service.execute(command);
        service.execute(command);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}