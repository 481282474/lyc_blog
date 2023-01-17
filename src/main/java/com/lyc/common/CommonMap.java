package com.lyc.common;

import com.lyc.entity.Param;
import com.lyc.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class CommonMap extends HashMap<String,Object> {

    @Autowired
    private ParamService paramService;

    /**
     * 项目启动加载参数
     */
    @PostConstruct
    public void init() {
        List<Param> paramList = null;

        try {
            paramList = paramService.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //放入所有参数，用于预加载
        for (Param param : paramList) {
            this.put(param.getParamName(), param.getParamValue());
        }
    }

}
