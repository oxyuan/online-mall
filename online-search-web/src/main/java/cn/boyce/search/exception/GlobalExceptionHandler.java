package cn.boyce.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 *
 * @Author: oxyuan
 * @Date: Created in 23:09 2019/5/5
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${DEFAULT_ERROR_VIEW}")
    private String DEFAULT_ERROR_VIEW;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {

        // 打印控制台
        e.printStackTrace();

        // 保存至log
        logger.error("e3-search系统发生错误", e);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);

        return modelAndView;
    }
}
