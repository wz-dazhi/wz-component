package com.wz.datasource.mybatisplus.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wz.common.model.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangzhi
 * @description: 请求分页基类
 * 请求示例:
 * <code>
 * <p>
 * public class CarRequest extends BasePage {
 * private String name;
 * }
 * </p>
 * <p>
 * public interface CarMapper extends BaseMapper {
 * IPage<T> findByPage(IPage<T> page, @Param("req") CarRequest req);
 * }
 * </p>
 * service使用
 * <p>
 * public IPage<T> getPage(CarRequest req) {
 * IPage<T> res = carMapper.findByPage(req.page(), req);
 * return res;
 * }
 * </p>
 * </code>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePage extends BaseDto {

    @ApiModelProperty("当前页")
    private long current = 1;

    @ApiModelProperty("每页显示条数，默认 10")
    private long size = 10;

    public <T> IPage<T> page() {
        return new Page<>(this.current, this.size);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
