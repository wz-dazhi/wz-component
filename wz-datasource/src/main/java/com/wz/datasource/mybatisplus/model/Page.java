package com.wz.datasource.mybatisplus.model;

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
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> implements IPage<T>, Serializable {

    @ApiModelProperty(value = "当前页", required = true)
    @Getter
    private long current = 1;

    @ApiModelProperty(value = "每页显示条数，默认 10", required = true)
    @Getter
    private long size = 10;

    @Getter
    @ApiModelProperty(value = "响应返回的列表")
    protected List<T> records = Collections.emptyList();

    @Getter
    @ApiModelProperty("总数量")
    protected long total = 0;

    @Getter
    @Setter
    @ApiModelProperty(hidden = true)
    protected List<OrderItem> orders = new ArrayList<>();

    @ApiModelProperty(hidden = true)
    protected boolean optimizeCountSql = true;

    @ApiModelProperty(hidden = true)
    protected boolean searchCount = true;

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

    @ApiModelProperty(hidden = true)
    protected boolean optimizeJoinOfCountSql = true;

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

    public Page(long current, long size, boolean searchCount) {
        this(current, size, 0, searchCount);
    }

    public Page(long current, long size, long total, boolean searchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.searchCount = searchCount;
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
        this.searchCount = isSearchCount;
        return this;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public Page<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    @ApiModelProperty("总页数")
    @Override
    public long getPages() {
        return super.getPages();
    }

    @ApiModelProperty(hidden = true)
    @Override
    public IPage<T> setPages(long pages) {
        super.setPages(pages);
        return this;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public boolean optimizeJoinOfCountSql() {
        return optimizeJoinOfCountSql;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public void setOptimizeJoinOfCountSql(boolean optimizeJoinOfCountSql) {
        this.optimizeJoinOfCountSql = optimizeJoinOfCountSql;
    }

    public static <T> Page<T> of(long current, long size) {
        return of(current, size, 0);
    }

    public static <T> Page<T> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T> Page<T> of(long current, long size, boolean searchCount) {
        return of(current, size, 0, searchCount);
    }

    public static <T> Page<T> of(long current, long size, long total, boolean searchCount) {
        return new Page<>(current, size, total, searchCount);
    }

    @Override
    public String toString() {
        // 打印全部
        if (this.total <= 20) {
            return JsonUtil.toJson(this);
        }

        // 数量太大, 只打印相关参数
        return "{\"current\":" + current + ",\"pages\":" + getPages() + ",\"size\":" + size + ",\"total\":" + total + "}";
    }

}
