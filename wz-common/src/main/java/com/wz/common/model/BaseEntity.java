package com.wz.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wz.common.util.JsonUtil;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @projectName: wz
 * @package: com.wz.common.model
 * @className: BaseEntity
 * @description:
 * @author: Zhi Wang
 * @date: 2019/4/30 5:35 PM
 * @version: 1.0
 **/
@Data
public class BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
