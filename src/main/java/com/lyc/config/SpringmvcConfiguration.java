package com.lyc.config;

import com.lyc.common.JacksonObjectMapper;
import com.lyc.filter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SpringmvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("admin/login");
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/paper").setViewName("admin/paper");
        registry.addViewController("/admin/category").setViewName("admin/category");
        registry.addViewController("/admin/commonParam").setViewName("admin/commonParam");
        registry.addViewController("/admin/personParam").setViewName("admin/personParam");
        registry.addViewController("/admin/aboutMe").setViewName("admin/aboutMe");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                        "/admin",
                        "/admin/login",
                        "/admin/logout",
                        "/admin/captcha**",
                        "/admin/assets/**",
                        "/admin/css/**",
                        "/admin/image/**",
                        "/admin/js/**");
    }

    /**
     * 扩展mvc框架消息转换器
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转化器对象
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象转换为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //mvc框架中本身就有默认的消息转换器，将自定义的消息转换器设置为优先使用
        //将上面的消息转换器对象追加到mvc框架的转换器集合中,索引为0意味优先使用
        converters.add(0,messageConverter);
    }
}
