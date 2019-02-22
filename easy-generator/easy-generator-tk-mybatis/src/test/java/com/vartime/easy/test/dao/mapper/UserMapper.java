package com.vartime.easy.test.dao.mapper;

import com.vartime.easy.spring.boot.tk.mybatis.core.mapper.CommonMapper;
import com.vartime.easy.test.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends CommonMapper<User> {
}