package com.wz.common.mapstruct;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: UserMapper
 * @description:
 * @author: zhi
 * @date: 2022/6/7
 * @version: 1.0
 */
//@Mapper(uses = {UserMapper.DateMapper.class, UserMapper.AddressMapper.class})
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
//        uses = {UserMapper.DateMapper.class, UserMapper.AddressMapper.class, ObjectCondition.class}
//)
@Mapper(config = UserMapperConfig.class)
public interface UserMapper extends MapMapper<User, UserDTO> {
    //UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Override
    default void beforeMapToSourceHandler(Map<String, String> map, User user) {
        updateTimeProcessor(map);
    }

    @Override
    default void beforeMapToTargetHandler(Map<String, String> map, UserDTO userDTO) {
        updateTimeProcessor(map);
    }

    default void updateTimeProcessor(Map<String, String> map) {
        String updateTime = map.get("updateTime");
        if (null == updateTime || updateTime.isBlank()) {
            return;
        }
        try {
            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(updateTime);
            map.put("updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //    @Component
//    class DateMapper {
//        public String asString(Date date) {
//            return date != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) : null;
//        }
//
//        public Date asDate(String date) {
//            if (null == date) {
//                return null;
//            }
//            try {
//                try {
//                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
//                } catch (ParseException e) {
//                    return new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(date);
//                }
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    @Component
    class AddressMapper {
        public String asString(User.Address address) {
            if (null == address) {
                return null;
            }
            return address.getProv() + "-" + address.getCity() + "-" + address.getCity();
        }

        public User.Address asAddress(String address) {
            if (null == address || address.isBlank()) {
                return null;
            }
            String[] s = address.split("-");
            if (s.length != 3) {
                return null;
            }

            User.Address a = new User.Address();
            a.setProv(s[0]);
            a.setCity(s[1]);
            a.setCoty(s[2]);
            return a;
        }
    }
}
