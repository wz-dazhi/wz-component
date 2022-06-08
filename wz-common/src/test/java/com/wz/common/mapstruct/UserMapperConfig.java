package com.wz.common.mapstruct;

import com.wz.common.mapstruct.condition.ObjectCondition;
import com.wz.common.mapstruct.uses.SimpleDateMapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: UserMapperConfig
 * @description:
 * @author: zhi
 * @date: 2022/6/7
 * @version: 1.0
 */
@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SimpleDateMapper.class, UserMapper.AddressMapper.class, ObjectCondition.class}
)
public interface UserMapperConfig {
}
