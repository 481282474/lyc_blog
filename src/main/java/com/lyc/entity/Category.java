package com.lyc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName category
 */
@TableName(value ="category")
@Data
public class Category implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 描述
     */
    @TableField(value = "descr")
    private String descr;

    /**
     * 背景颜色
     */
    @TableField(value = "color")
    private String color;

    /**
     * 图片url
     */
    @TableField(value = "img_url")
    private String imgUrl;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}