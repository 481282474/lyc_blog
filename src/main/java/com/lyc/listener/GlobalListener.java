package com.lyc.listener;

import com.lyc.common.CommonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听器，触发某些事件自动执行某些操作
 * @author 刘怡畅
 * @date 2023/01/03 16:45
 */

@Component
public class GlobalListener implements ServletContextListener {

    @Autowired
    private CommonMap commonMap;

    //项目初始化时获取所有参数
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("paramMap",commonMap);
    }

    //释放资源
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute("paramMap");
        commonMap.clear();
    }
}
