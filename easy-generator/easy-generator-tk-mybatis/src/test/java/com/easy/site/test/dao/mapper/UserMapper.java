package com.easy.site.test.dao.mapper;

import com.easy.site.spring.boot.tk.mybatis.core.mapper.CommonMapper;
import com.easy.site.test.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends CommonMapper<User> {
}
