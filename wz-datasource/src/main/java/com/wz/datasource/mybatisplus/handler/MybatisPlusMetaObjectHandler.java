package com.wz.datasource.common.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wz.datasource.common.model.BaseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @projectName: wz-component
 * @package: com.wz.datasource.mybatisplus.handler
 * @className: MybatisPlusMetaObjectHandler
 * @description:
 * @author: zhi
 * @date: 2020/12/30 下午2:54
 * @version: 1.0
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    private static final Class<LocalDateTime> LOCAL_DATE_TIME_CLASS = LocalDateTime.class;

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (log.isDebugEnabled()) {
            log.debug("insert fill starting...");
        }
        final Supplier<LocalDateTime> now = LocalDateTime::now;
        Object createTime = getFieldValByName(BaseBean.FIELD_CREATE_TIME, metaObject);
        if (Objects.isNull(createTime)) {
            this.strictInsertFill(metaObject, BaseBean.FIELD_CREATE_TIME, now, LOCAL_DATE_TIME_CLASS);
        }
        Object updateTime = getFieldValByName(BaseBean.FIELD_UPDATE_TIME, metaObject);
        if (Objects.isNull(updateTime)) {
            this.strictInsertFill(metaObject, BaseBean.FIELD_UPDATE_TIME, now, LOCAL_DATE_TIME_CLASS);
        }
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (log.isDebugEnabled()) {
            log.debug("update fill starting...");
        }
        Object updateTime = getFieldValByName(BaseBean.FIELD_UPDATE_TIME, metaObject);
        if (Objects.isNull(updateTime)) {
            this.strictUpdateFill(metaObject, BaseBean.FIELD_UPDATE_TIME, LocalDateTime::now, LOCAL_DATE_TIME_CLASS);
        }
    }

}
