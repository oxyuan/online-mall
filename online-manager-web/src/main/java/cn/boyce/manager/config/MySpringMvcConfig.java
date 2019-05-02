package cn.boyce.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 20:29 2019/4/20
 **/
@Configuration
public class MySpringMvcConfig implements WebMvcConfigurer {

    /**
     * WebMvcConfigurerAdapter 在 SpringBoot 2.x 过时，不过还可以用
     * 然而推荐的 WebMvcConfigurationSupport 自动配置全部不再生效
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }
}