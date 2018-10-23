package com.venusz.admin.common;

import com.venusz.admin.exception.AdminException;
import com.venusz.admin.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理
 */
@Component
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        String url = httpServletRequest.getRequestURI().toString();
        ModelAndView mv;
        String defaultMsg = "System error!";
        //.json   or   .page
        //项目所有请求json数据，都使用.json结尾
        if(url.endsWith(".json")){
            if(e instanceof AdminException || e instanceof ParamException){
                JsonData result = JsonData.fail(e.getMessage());
                mv = new ModelAndView("jsonView",result.toMap());
            }else {
                log.error("unknow json exception,url:{}",url,e);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView",result.toMap());
            }
        }else if(url.endsWith(".page")){//项目所有请求page页面，都使用.page结尾
            log.error("unknow page exception,url:{}",url,e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception",result.toMap());
        }else {
            log.error("unknow exception,url:{}",url,e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView",result.toMap());
        }
        return mv;
    }
}
