package com.venusz.admin.controller;

import com.venusz.admin.common.JsonData;
import com.venusz.admin.param.DeptParam;
import com.venusz.admin.param.UserParam;
import com.venusz.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/sys/user")
@Controller
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(UserParam userParam){
        sysUserService.save(userParam);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(UserParam userParam){
        sysUserService.update(userParam);
        return JsonData.success();
    }


}