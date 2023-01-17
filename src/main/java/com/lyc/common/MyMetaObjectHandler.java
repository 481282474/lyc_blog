package com.lyc.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义数据对象处理器
 * @author 刘怡畅
 * @date 2022/12/28 14:23
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动填充，插入操作
     * 前提是在实体类的属性名上添加 @TableField(fill=FieldFill.INSERT_UPDATE)
     * @param metaObject
     */

    @Override
    public void insertFill(MetaObject metaObject) {

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}