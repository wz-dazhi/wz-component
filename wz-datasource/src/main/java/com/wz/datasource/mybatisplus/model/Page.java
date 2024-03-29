package com.wz.datasource.mybatisplus.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wz.common.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wangzhi
 * @description: 请求分页基类
 * 请求示例:
 * <code>
 * <p>
 * public class CarRequest extends Page {
 * private String name;
 * }
 * </p>
 * <p>
 * public interface CarMapper extends BaseMapper {
 * IPage<T> findByPage(@Param("req") CarRequest req);
 * }
 * </p>
 * service使用
 * <p>
 * public IPage<T> getPage(CarRequest req) {
 * IPage<T> res = carMapper.findByPage(req);
 * return res;
 * }
 * </p>
 * </code>
 */
@ApiModel("分页请求")
@JsonIgnoreProperties(value = {"orders",
        "optimizeCountSql",
        "searchCount",
        "hitCount",
        "countId",
        "maxLimit"},
        allowSetters = true)
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> implements Serializable {

    @ApiModelProperty(value = "当前页", required = true)
    @Getter
    @Setter
    private long current = 1;

    @ApiModelProperty(value = "每页显示条数，默认 10", required = true)
    @Getter
    @Setter
    private long size = 10;

    @Getter
    @Setter
    @ApiModelProperty(value = "响应返回的列表")
    protected List<T> records = Collections.emptyList();

    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    protected long total = 0;

    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    protected List<OrderItem> orders = new ArrayList<>();

    @Setter
    @ApiModelProperty(hidden = true)
    protected boolean optimizeCountSql = true;

    @Setter
    @ApiModelProperty(hidden = true)
    protected boolean isSearchCount = true;

    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    protected boolean hitCount = false;

    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    protected String countId;

    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    protected Long maxLimit;


    public Page() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Page(long current, long size) {
        this(current, size, 0);
    }

    public Page(long current, long size, long total) {
        this(current, size, total, true);
    }

    public Page(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public Page(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    @Override
    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public Page<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public Page<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public Page<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public IPage<T> setPages(long pages) {
        super.setPages(pages);
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

}
