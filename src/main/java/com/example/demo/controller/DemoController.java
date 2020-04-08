package com.example.demo.controller;


import com.example.demo.service.DemoService;
import com.example.demo.util.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zhangzhiqiang1
 * @Date 2019/11/6 15:58
 * @Version 1.0
 **/
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;


    @RequestMapping("/test")
    @ResponseBody
    //@LoginRequired
    public ResultVO test(){

        Map data =new HashMap();
        data.put("loginName","17090307694");
        data.put("password","e10adc3949ba59abbe56e057f20f883e");
        data.put("enterprise","ZHL0f2ce7b9");


        return  new ResultVO.ResultVoBuilder<String>().code(0).msg("登录成功").result("你好吗").builder();
    }

    /**
     * 跳转到指定页面
     * @Author zhangzhiqiang1
     * @Date  2020/4/8 17:21
     * @Param []
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequestMapping("/toUserPage")
    public ModelAndView toUserPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/user");
        return modelAndView;
    }


}
