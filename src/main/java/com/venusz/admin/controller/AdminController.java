package com.venusz.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: VenusZ
 * \* Date: 2018/10/30
 * \* Time: 22:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    @RequestMapping("/index.page")
    public ModelAndView index(){
        return new ModelAndView("admin");
    }

}
