package com.lyc.common.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 个人参数实体类
 * @author 刘怡畅
 * @date 2023/1/15 17:34
 */

@Data
public class PerParamVo implements Serializable {

    private String QQ;

    //微信
    private String wx;

    private String email;

    private String github;

    private String csdn;

}
