package com.lyc.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName paper
 */
@TableName(value ="paper")
@Data
public class Paper implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 摘要
     */
    @TableField(value = "sub_content")
    private String subContent;

    /**
     * markdown 正文
     */
    @TableField(value = "content")
    private String content;

    /**
     * 显示状态 1：是 0：否
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 分类ID
     */
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * 标签
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 图片
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 
     */
    @TableField(value = "create_time",fill= FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "update_time",fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}