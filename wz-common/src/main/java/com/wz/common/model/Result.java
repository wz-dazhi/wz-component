package com.wz.common.model;

import com.wz.common.enums.ResultEnum;
import com.wz.common.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @package: com.common.model
 * @className: Result
 * @description: 结果响应
 * @author: Zhi Wang
 * @createDate: 2018/9/9 上午12:27
 **/
@ApiModel("统一响应结果")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * code码
     */
    @ApiModelProperty("响应码")
    private String code;

    /**
     * msg
     */
    @ApiModelProperty("响应信息")
    private String msg;

    /**
     * data
     */
    @ApiModelProperty("响应数据")
    private T data;

    @ApiModelProperty("是否成功")
    public boolean isSuccess() {
        return ResultEnum.OK.code().equals(code);
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
