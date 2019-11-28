package com.example.demo.service.Impl;

import com.example.demo.dao.DemoMapper;
import com.example.demo.dto.User;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zhangzhiqiang1
 * @Date 2019/11/6 16:25
 * @Version 1.0
 **/
@Service
public class DemoServiceImpl implements DemoService {

    @Resource
    private DemoMapper demoMapper;

    @Override
    public User getOne() {
      return demoMapper.getOne();
    }
}
