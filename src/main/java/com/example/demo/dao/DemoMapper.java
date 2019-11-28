package com.example.demo.dao;

import com.example.demo.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface DemoMapper {

    User getOne();
}
