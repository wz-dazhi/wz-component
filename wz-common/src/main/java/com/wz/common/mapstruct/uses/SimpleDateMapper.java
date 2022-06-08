package com.wz.common.mapstruct.uses;

import com.wz.common.constant.DateConsts;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct.uses
 * @className: SimpleDateMapper
 * @description:
 * @author: zhi
 * @date: 2022/6/8
 * @version: 1.0
 */
@Component
public class SimpleDateMapper extends AbstractDateMapper {

    @Override
    protected SimpleDateFormat createSimpleDateFormat() {
        return new SimpleDateFormat(DateConsts.NORMAL_YYYY_MM_DD_HH_MM_SS_PATTERN);
    }

}
