package com.wz.common.json.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @projectName: wz-common
 * @package: com.wz.common.json.ser
 * @className: SerializerBigDecimal
 * @description:
 * @author: Zhi
 * @date: 2019-11-01 15:04
 * @version: 1.0
 */
public class SerializerBigDecimal extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeNull();
        } else {
            // 这里取floor
            gen.writeNumber(value.setScale(2, RoundingMode.FLOOR));
        }
    }

}