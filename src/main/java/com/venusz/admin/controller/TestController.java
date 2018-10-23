package com.venusz.admin.controller;

import com.venusz.admin.common.ApplicationContextHelper;
import com.venusz.admin.common.JsonData;
import com.venusz.admin.dao.SysAclMapper;
import com.venusz.admin.dao.SysAclModuleMapper;
import com.venusz.admin.exception.ParamException;
import com.venusz.admin.model.SysAclModule;
import com.venusz.admin.param.TestVo;
import com.venusz.admin.util.BeanValidator;
import com.venusz.admin.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/test/")
public class TestController {

    @RequestMapping("hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello zhang");
        return JsonData.success("hello admin");
    }

    @RequestMapping("validate.json")
    @ResponseBody
    public JsonData validate(TestVo testVo){
        try {
            Map<String,String> map = BeanValidator.validateObject(testVo);
            if(MapUtils.isEmpty(map)){
                map.entrySet().forEach(stringStringEntry -> log.info("{}-->{}",stringStringEntry.getKey(),stringStringEntry.getValue()));
            }
        }catch (Exception e){

        }
        return JsonData.success("test validate");
    }

    @RequestMapping("param.json")
    @ResponseBody
    public JsonData param(TestVo testVo) throws ParamException {
        log.info("test validate");
        BeanValidator.check(testVo);
        return JsonData.success("test param");
    }

    @RequestMapping("mapper.json")
    @ResponseBody
    public JsonData mapper(TestVo testVo) throws ParamException {
        log.info("test validate");
        SysAclModuleMapper sysAclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(1);
        log.info(JsonUtils.obj2String(sysAclModule));
        BeanValidator.check(testVo);
        return JsonData.success("test param");
    }
}
