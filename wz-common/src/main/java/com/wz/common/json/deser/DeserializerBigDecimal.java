package com.wz.common.json.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @projectName: wz-common
 * @package: com.wz.common.json.deser
 * @className: DeserializerBigDecimal
 * @description:
 * @author: Zhi
 * @date: 2019-11-01 15:02
 * @version: 1.0
 */
public class DeserializerBigDecimal extends JsonDeserializer<BigDecimal> {

    /**
     * 出参保留两位小数
     */
    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (Objects.isNull(jsonParser.getDecimalValue())) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR);
        } else {
            // 这里取floor
            return jsonParser.getDecimalValue().setScale(2, RoundingMode.FLOOR);
        }
    }

}