package com.wz.common.model;

import com.wz.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: wz
 * @package: com.common.model
 * @className: Result
 * @description: 结果响应
 * @author: Zhi Wang
 * @createDate: 2018/9/9 上午12:27
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * code码
     */
    private String code;

    /**
     * msg
     */
    private String msg;

    /**
     * data
     */
    private T data;

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
