package com.wz.datasource.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wz.common.util.JsonUtil;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @projectName: wz-datasource
 * @package: com.wz.datasource.mybatisplus.model
 * @className: BaseBean
 * @description:
 * @author: Zhi Wang
 * @date: 2019/4/30 5:35 PM
 * @version: 1.0
 **/
@Data
public class BaseBean implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_CREATE_TIME = "create_time";
    public static final String COL_UPDATE_TIME = "update_time";
    public static final String FIELD_CREATE_TIME = "createTime";
    public static final String FIELD_UPDATE_TIME = "updateTime";

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

}
